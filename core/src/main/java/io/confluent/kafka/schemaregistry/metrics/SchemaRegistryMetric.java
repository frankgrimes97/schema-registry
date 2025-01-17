/*
 * Copyright 2020 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.kafka.schemaregistry.metrics;

import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.metrics.MeasurableStat;
import org.apache.kafka.common.metrics.Metrics;
import org.apache.kafka.common.metrics.Sensor;
import org.apache.kafka.common.metrics.stats.Value;

import java.util.concurrent.atomic.AtomicLong;

public class SchemaRegistryMetric {
  @Deprecated
  private final AtomicLong count = new AtomicLong();
  private final Sensor sensor;

  @Deprecated
  public SchemaRegistryMetric(Metrics metrics, String sensorName, MetricName metricName) {
    this(metrics, sensorName, metricName, new Value());
  }

  public SchemaRegistryMetric(Metrics metrics, String sensorName, MetricName metricName,
                              MeasurableStat measurableStat) {
    // a new sensor is only created if the sensor name doesn't already exist in metrics
    sensor = metrics.sensor(sensorName);
    // a new metric is only created if the metric name doesn't already exist in the sensor
    sensor.add(metricName, measurableStat);
  }

  @Deprecated
  public void increment() {
    sensor.record(count.addAndGet(1));
  }

  @Deprecated
  public void set(long value) {
    count.set(value);
    sensor.record(value);
  }

  @Deprecated
  public long get() {
    return count.get();
  }

  public void record() {
    // equivalent to record(1.0);
    sensor.record();
  }

  public void record(double value) {
    sensor.record(value);
  }
}
