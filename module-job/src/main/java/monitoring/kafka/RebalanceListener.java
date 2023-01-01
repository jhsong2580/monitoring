package monitoring.kafka;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

@Slf4j
public class RebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsLost(Collection<TopicPartition> partitions) {
        ConsumerRebalanceListener.super.onPartitionsLost(partitions);
    }

    @Override// Rebalance 시작하기 직전 호출되는 메서드.
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        log.warn("Partition are revoked");
        // 보통 장애가 나서 Rebalance가 시작이 될때, 작업이 종료된 Record에 대한 Commit작업을 이 함수 내에서 진행한다.
    }

    @Override// 리밸런스가 끝난 뒤에 파티션이 할당 완료되면 호출되는 메서드
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        log.warn("Partition are assigned");
    }
}
