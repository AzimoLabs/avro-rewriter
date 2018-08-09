# Avro Re-Writer
Reads, transforms and writes avro files written on Google Cloud Storage with use of Generic Avro Records.

## Deploy streaming job to Google Dataflow
### Prerequisites
 * You need to have correct Dataflow permissions to deploy a job
 * Install google cloud sdk locally. Download [here](https://cloud.google.com/sdk/)
 * Build fat jar: 
 ```
 ./gradlew clean build
 ``` 
 
### Deploy
Replace the following parameters with your own:
* project - Google cloud project
* baseInputPath - root path with your input data set
* baseOutputPath - root destination path
* inputMessageType - specific message type (subfolder on storage) that we want to transform in this execution. If you have used kafka-to-avro writer this will simply be your avro message type.
* transformClass - Implementation of transformation class needs to extend com.azimo.avro.rewriter.transform.AvroBaseTr
* network - Dataflow configuration of network: this is an optional parameter which you can omit in case you want to use google cloud platform defaults
* subnetwork - Dataflow configuration of subnetwork: this is an optional parameter which you can omit in case you want to use google cloud platform defaults  

**Launch the following command:**  
```
java -jar build/libs/avro-rewriter-*.jar --runner=DataflowRunner \
                   --project=gcp_project \
                   --jobName=avro-rewriter \
                   --tempLocation=gs://gcp_bucket/temp/avro-rewriter \
                   --stagingLocation=gs://gcp_bucket/stream/staging/avro-rewriter \
                   --numberOfShards=1 \
                   --zone=europe-west1-b \
                   --network=gcp_network \
                   --subnetwork=gcp_subnetwork \
                   --baseInputPath=gs://gcs_bucket/mds/facts \
                   --baseOutputPath=gs://gcs_bucket/mds/facts_transformed \
                   --inputMessageType=AvroMessageType \
                   --transformClass=com.azimo.avro.rewriter.transform.TransformTr
```
                   
**Other configuration parameters:**
* runner - runner for apache beam
* jobName - Dataflow job name
* numberOfShards - defines how many files will be created after transformation
* tempLocation - temp directory for Dataflow job on google cloud storage
* stagingLocation - staging directory for Dataflow job on google cloud storage 

# License

    Copyright (C) 2016 Azimo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.      