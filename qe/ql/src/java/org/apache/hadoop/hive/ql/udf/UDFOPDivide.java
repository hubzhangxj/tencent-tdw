/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.description;
import org.apache.hadoop.hive.serde2.io.ByteWritable;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.io.ShortWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.hive.ql.exec.NumericOpMethodResolver;
import org.apache.hadoop.hive.ql.metadata.DivideZeroHiveException;

@description(name = "/", value = "a _FUNC_ b - Divide a by b", extended = "Example:\n"
    + "  > SELECT 3 _FUNC_ 2 FROM src LIMIT 1;\n" + "  1.5")
public class UDFOPDivide extends UDF {

  private static Log LOG = LogFactory
      .getLog("org.apache.hadoop.hive.ql.udf.UDFOPDivide");

  protected DoubleWritable doubleWritable = new DoubleWritable();

  public DoubleWritable evaluate(DoubleWritable a, DoubleWritable b)
      throws DivideZeroHiveException {
    if ((a == null) || (b == null))
      return null;
    double res = a.get() / b.get();
    if (Math.abs(res) >= Double.MAX_VALUE) {
      if (Math.abs(b.get()) < Double.MIN_NORMAL)
        LOG.info("the divider is zero:  " + b.get());
      else
        LOG.info("the result of divide is too large that Double can not express...");

      System.exit(1);
    }
    doubleWritable.set(res);
    return doubleWritable;
  }
}
