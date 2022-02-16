const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const { SlowBuffer } = require('buffer');
const fs = require('fs');
const { exit } = require('process');

const url = 'mongodb://localhost:27017';
const dbName = 'knowledgeEnvironment';

// Give usage command if no packageIds provided

const getNextLetterInAlphabet =(currentLetter = '')  => {

    if(currentLetter === "") {
        return "A";
    }
    var prefix = currentLetter.substring(0, currentLetter.length-1);
    var last = currentLetter[currentLetter.length-1]
    if(last === "Z") {
        return (getNextLetterInAlphabet(prefix) + "A");
    }
    return prefix + String.fromCharCode(currentLetter.charCodeAt(currentLetter.length-1) + 1);

}

const getGridOverlay = (metadata) => {

    let lineThickness = 13;
    let vertical = 500 / parseFloat(metadata.openslide.mpp_y);
    let horizontal = 500 / parseFloat(metadata.openslide.mpp_y);
    let overlay = [];
    let overlayLabel = []
    if (metadata && metadata.aperio && metadata.aperio.OriginalHeight && metadata.aperio.OriginalWidth) {
        let width = parseInt(metadata.aperio.OriginalWidth);
        let height = parseInt(metadata.aperio.OriginalHeight);
        let gridLineLengthForHeight = (Math.ceil(((height + horizontal) / horizontal)) -1 ) * horizontal;
        let gridLineLengthForWidth = (Math.ceil(((width + horizontal) / horizontal)) -1 ) * horizontal;

        for (let i = 0; i < (width + vertical); i += vertical) {
            overlay.push({
                px: i,
                py: 0,
                width: lineThickness,
                height: gridLineLengthForHeight,
                className: 'gridline'
            })
        }

        for (let i = 0; i <= (height + horizontal); i += horizontal) {
            overlay.push({
                px: 0,
                py: i,
                width: gridLineLengthForWidth,
                height: lineThickness,
                className: 'gridline'
            })
        }
        let currentLetter = '';
        let currentNumber = 0;
        for (let yy = 0; yy < (height); yy += vertical) {
            currentLetter = getNextLetterInAlphabet('');
            for (let i = 0; i < (width); i += vertical) {
                overlayLabel.push(`${currentLetter + currentNumber}`)
                overlay.push({
                    id: `labelOverlay-${currentLetter + currentNumber}`,
                    px: 0 + (i / vertical * vertical + lineThickness),
                    py: 0 + (yy / horizontal * horizontal + lineThickness),
                })
                currentLetter = getNextLetterInAlphabet(currentLetter);
            }
            currentNumber += 1;
        }

    } else {
        console.error('Metadata not provided with slide')
    }
    return [overlay, overlayLabel];
}

const updateSlides = function (db, callback) {
    var patientCollection = db.collection('patients');

    patientCollection.find({}).toArray( function(err, documents) {
        assert.strictEqual(null, err);
        let asyncWrapper = new Promise((resolve, reject) => {

            documents.forEach(function(document, index, array) {
                
                document.slides.forEach(function(slide, index, array) {
                    let metadata = slide.metadata;
                    let [overlay, overlayLabel] = getGridOverlay(metadata);
                    metadata.overlay = overlay;
                    metadata.overlayLabel = overlayLabel;
                    slide['metadata'] = metadata;
                    
                });
                patientCollection.update({_id: document._id}, document);
            });
            resolve();
        });
        asyncWrapper.then(() => {
            callback();
        });
    });    
}

MongoClient.connect(url, function(err, client) {
    assert.strictEqual(null, err);
    console.log("successfully connected to server");

    const db = client.db(dbName);
    
    updateSlides(db, function() {
        client.close();
        process.exit(0)
    });

});

