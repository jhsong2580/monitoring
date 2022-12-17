# kafka broker properties (config/server.properties)


|         파라미터         |                입력값                |                     설명                     |  
|:--------------------:|:---------------------------------:|:------------------------------------------:|
|      broker.id       |                 0                 |  이 broker의 ID를 설정. 각 Broker는 ID가 달라야  한다   |
|      listeners       |    PLAINTEXT://localhost:9092     | kafka client가 broker와 통신을 할때 바라보는 ENDPOINT |
| advertised.listeners |    PLAINTEXT://localhost:9092     | kafka client가 broker와 통신을 할때 바라보는 ENDPOINT |
| log.dirs | /home/kafka/kafka_2.12-2.5.0/data |           broker의 LOG가 담길 DIR 위치           |
| num.partitions=1 |                 3                 |    토픽 별 파티션 개수. 최대 broker개수만큼 설정이 가능하다     |


# CLI
- ./kafka-broker-api-versions.sh --bootstrap-server localhost:9092
  - broker 내부에서 설정된 정보
- ./kafka-topics.sh --bootstrap-server localhost:9092 --list
  - broker 내부에 등록된 토픽 리스트

# kafka-topics.sh
- kafka-topics.sh --create --bootstrap-server localhost:9092 --topic hello.kafa
  - hello.kafa 토픽 생성


### kafka topic 생성
./kafka-topics.sh --bootstrap-server my-kafka:9092 --topic hello.kafka --describe
Topic: hello.kafka	PartitionCount: 1	ReplicationFactor: 1	Configs: segment.bytes=1073741824
Topic: hello.kafka	Partition: 0	Leader: 0	Replicas: 0	Isr: 0

### kafka topic - partition 10개로 생성
./kafka-topics.sh --create --bootstrap-server my-kafka:9092 --partitions 10 --replication-factor 1 --topic hello.kafka2

### kafka topic - partition 증설 (감설은 안됨)
./kafka-topics.sh --bootstrap-server my-kafka:9092 --topic test --alter --partitions 2


# kafka-configs.sh
- 토픽의 일부 옵션을 설정하기 위한 명령어 
- --alter, --add-config를 통해 토픽별 적용 가능

### kafka topic replicas 변경
- ./kafka-configs.sh  --bootstrap-server my-kafka:9092 --alter --add-config min.insync.replicas=2 --topic test

# kafka-console-producer.sh
- 토픽에 데이터를 넣을수 있는 명령어

### key값이 없는 데이터 삽입 (default key : null)
./kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic hello.kafka 

### key값이 있는 데이터 삽입
- ./kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic hello.kafka --property "parse.key="true" --property "key.separator=:"
> key1:no1
> key2:no2
> key3:no3
- 값을 삽입하며 key1 로 no1이라는 값을, key2로 no2라는 값을, key3으로 no3이라는 값을 넣는다. 

# kafka-console-consumer.sh
- 특정 topic에 있는 데이터를 조회

### topic에 있는 가장 처음 데이터 부터 조회하기
./kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic hello.kafka  --from-beginning

### topic에 있는 값에서 메세지 키와 레코드를 확인
./kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic hello.kafka  --from-beginning --property print.key-true --property key.separator="-"

### topic에 있는 레코드에서 출력 제한걸기(limit)
./kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic hello.kafka  --from-beginning --max-messages 3

### 특정 partition의 레코드들 가져오기
./kafka-console-consumer.sh  --bootstrap-server my-kafka:9092 --topic hello.kafka --partition 3

### Consumer group 기준 Record기반으로 데이터 가져오기
./kafka-console-consumer.sh  --bootstrap-server my-kafka:9092 --topic hello.kafka  --max-messages 2 --group hello-group

# kafka-consumer-groups.sh
- 컨슈머 그룹 관리

### 컨슈머 그룹이 어떤 토픽을 대상으로 가져갔는지 상태 확인 가능한 describe
- 파티션 번호, 현재까지 가져간 오프셋, 파티션 마지막 레코드 오프셋 등 컨슈머 상태 조회 가능 
- ./kafka-consumer-groups.sh --bootstrap-server my-kafka:9092 --group hello-group --describe

### 컨슈머 그룹 오프셋 리셋(어느 오프셋부터 그룹이 다시 가져갈지 설정)
- hello-group의 hello-kafka topic에서 가장 낮은 숫자의 레코드를(가장앞의) 다시 가져가게 동작
  ./kafka-consumer-groups.sh --bootstrap-server my-kafka:9092  --group hello-group --topic hello.kafka --reset-offsets --to-earliest --execute 
- 오프셋 리셋 종류
  - to-earlest : 가장 처음 오프셋(가장 작은 번호)로 리셋
  - to-latest : 가장 마지막 오프셋(가장 큰 번호)
  - to-current : 현시점 기준 오프셋
  - to-datetime {YYYY-MM-DDTHH:mmSS.sss} 특정 일시 오프셋 리셋(Record Timestamp기준)
  - to-offset {long} 특정 오프셋으로 리셋
  - shift-by {+/- long} 현재 오프셋 앞뒤로 long만큼 리셋

### ./kafka-consumer-groups.sh --bootstrap-server my-kafka:9092 --group hello-group --describe

Consumer group 'hello-group' has no active members.

GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
hello-group     hello.kafka     0          0               8               8    
- hello-group에 hello.kafka라는 토픽이 있고
- 해당 TOPIC의 0번 Partition의 Offset은 0이고, 마지막 Offset은 8이며, 현재 Offset과 마지막 Offset(읽어야할 레코드) 개수는 8이다(LAG)
- LAG값이 많아지면 들어오는 Record가 더 많기때문에 Consumer증설을 고려해야한다. 