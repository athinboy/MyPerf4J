package cn.myperf4j.base.metric.processor.influxdb;

import cn.myperf4j.base.config.ProfilingConfig;
import cn.myperf4j.base.metric.JvmBufferPoolMetrics;
import cn.myperf4j.base.metric.processor.AbstractJvmBufferPoolMetricsProcessor;

/**
 * Created by LinShunkang on 2018/8/25
 */
public class InfluxDBJvmBufferPoolMetricsProcessor extends AbstractJvmBufferPoolMetricsProcessor {

    private ThreadLocal<StringBuilder> sbThreadLocal = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder(128);
        }
    };

    @Override
    public void process(JvmBufferPoolMetrics metrics, long processId, long startMillis, long stopMillis) {
        StringBuilder sb = sbThreadLocal.get();
        try {
            logger.log(createLineProtocol(metrics, startMillis * 1000 * 1000L, sb));
        } finally {
            sb.setLength(0);
        }
    }

    private String createLineProtocol(JvmBufferPoolMetrics metrics, long startNanos, StringBuilder sb) {
        sb.append("jvm_buffer_pool_metrics")
                .append(",AppName=").append(ProfilingConfig.getInstance().getAppName())
                .append(",PoolName=").append(metrics.getName())
                .append(" Count=").append(metrics.getCount()).append('i')
                .append(",MemoryUsed=").append(metrics.getMemoryUsed()).append('i')
                .append(",MemoryCapacity=").append(metrics.getMemoryCapacity()).append('i')
                .append(' ').append(startNanos);
        return sb.toString();
    }
}
