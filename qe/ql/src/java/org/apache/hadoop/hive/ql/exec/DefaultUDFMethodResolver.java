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

package org.apache.hadoop.hive.ql.exec;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

public class DefaultUDFMethodResolver implements UDFMethodResolver {

  private Class<? extends UDF> udfClass;

  public DefaultUDFMethodResolver(Class<? extends UDF> udfClass) {
    this.udfClass = udfClass;
  }

  @Override
  public Method getEvalMethod(List<TypeInfo> argClasses)
      throws AmbiguousMethodException {
    Method m = FunctionRegistry.getMethodInternal(udfClass, "evaluate", false,
        argClasses);
    if (m == null) {
      throw new AmbiguousMethodException(udfClass, argClasses);
    }
    return m;
  }
}
