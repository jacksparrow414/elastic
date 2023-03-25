# elasticsearch cluster and java client example

# setup elasticsearch cluster
[Elastcisearch doc](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-compose-file)
## configuration different from the official documentation
```yaml
es01:
  ports:
    - 19200:9200
es02:
  ports:
    - 19201:9200
  environment:
    - ELASTIC_PASSWORD=${ELASTIC_PASSWORD}
es03:
  ports:
    - 19202:9200
  environment:
    - ELASTIC_PASSWORD=${ELASTIC_PASSWORD}
kibana:
  environment:
    - ELASTICSEARCH_HOSTS=["https://es01:9200","https://es02:9200","https://es03:9200"]
```
# replace ca.crt
replace the ca.crt in the source code src/main/resources/ca directory with your own ca.crt
# test connect to elasticsearch cluster
1. run ElasticSearchConfigurationTest
2. shut down the master node, there are two nodes available in the cluster, and one of them will be elected as the new master node. then run ElasticSearchConfigurationTest
3. shut down the master node, only one node in the cluster is available, and the cluster is no longer available. the run ElasticSearchConfigurationTest
