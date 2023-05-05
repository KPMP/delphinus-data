const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const { SlowBuffer } = require('buffer');
const fs = require('fs');

const url = 'mongodb://localhost:27017';
const dbName = 'dataLake';
const slideTypeMap = { 'PAS':'pas', 'FRZ': 'frz', 'HE': 'he', 'SIL': 'silver', 
    'TOL': 'tol', 'TRI': 'tri', 'CR': 'cr', 'IHC': 'ihc'}

let dprCommands = [];

let packageIds = process.argv.slice(2);

// Give usage command if no packageIds provided

MongoClient.connect(url, {useUnifiedTopology: true}, function(err, client) {
    assert.strictEqual(null, err);
    console.log("successfully connected to server");

    const db = client.db(dbName);
    var packageCollection = db.collection('packages');

    

    for(i=0; i< packageIds.length; i++) {
	let packageId = packageIds[i]
        packageCollection.findOne({ _id: packageId }, function(err, document) {
            assert.strictEqual(null, err);

            let updateCommands = '#/bin/sh\n';
            let kpmpId = document.subjectId;

            document.files.forEach(fileInfo => {
                let fileName = fileInfo.fileName.replace(/\.svs/g, '');
                let stainType = '';
                for(const key in slideTypeMap) {
                    if (fileName.includes(key)) {
                        stainType = slideTypeMap[key];
                    } 
                }
                updateCommands = updateCommands.concat('\n', './run-wsi-worker.sh ' + kpmpId + ' ' + fileName + ' ' + fileInfo._id + ' ' + stainType ); 
            });

            fs.writeFile('dprUpdate_' + packageId + '.sh', updateCommands, { flag: 'a'}, function(err) {
                if (err) {
                    return console.log(err);
                }
                
            });
        });    
        
        }
        client.close();
});

