/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.taier.common.enums;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public enum EComponentType {

    FLINK(0, "Flink", "flinkConf",EComponentScheduleType.COMPUTE),
    SPARK(1, "Spark", "sparkConf",EComponentScheduleType.COMPUTE),
    HDFS(2, "HDFS", "hadoopConf",EComponentScheduleType.STORAGE),
    YARN(3, "YARN", "yarnConf",EComponentScheduleType.RESOURCE),
    SPARK_THRIFT(4, "SparkThrift", "hiveConf",EComponentScheduleType.COMPUTE),
    HIVE_SERVER(5, "HiveServer", "hiveServerConf",EComponentScheduleType.COMPUTE),
    SFTP(6, "SFTP", "sftpConf",EComponentScheduleType.COMMON),
    OCEAN_BASE(7, "OceanBase", "oceanBaseConf",EComponentScheduleType.COMPUTE),

    ;

    private Integer typeCode;

    private String name;

    private String confName;

    private EComponentScheduleType componentScheduleType;


    EComponentType(int typeCode, String name, String confName,EComponentScheduleType componentScheduleType) {
        this.typeCode = typeCode;
        this.name = name;
        this.confName = confName;
        this.componentScheduleType = componentScheduleType;
    }

    private static final Map<Integer, EComponentType> COMPONENT_TYPE_CODE_MAP = new ConcurrentHashMap<>(16);
    private static final Map<String, EComponentType> COMPONENT_TYPE_NAME_MAP = new ConcurrentHashMap<>(16);
    private static final Map<String, EComponentType> COMPONENT_TYPE_CONF_NAME_MAP = new ConcurrentHashMap<>(16);

    static {
        for (EComponentType componentType : EComponentType.values()) {
            COMPONENT_TYPE_CODE_MAP.put(componentType.getTypeCode(), componentType);
            COMPONENT_TYPE_NAME_MAP.put(componentType.getName(), componentType);
            COMPONENT_TYPE_CONF_NAME_MAP.put(componentType.getConfName(), componentType);
        }
    }

    public static EComponentType getByCode(int code) {
        EComponentType componentType = COMPONENT_TYPE_CODE_MAP.get(code);
        if (Objects.nonNull(componentType)) {
            return componentType;
        }

        throw new IllegalArgumentException("No enum constant with type code:" + code);
    }

    public static EComponentType getByName(String name) {
        EComponentType componentType = COMPONENT_TYPE_NAME_MAP.get(name);
        if (Objects.nonNull(componentType)) {
            return componentType;
        }

        throw new IllegalArgumentException("No enum constant with name:" + name);
    }

    public static EComponentType getByConfName(String confName) {
        EComponentType componentType = COMPONENT_TYPE_CONF_NAME_MAP.get(confName);
        if (Objects.nonNull(componentType)) {
            return componentType;
        }

        throw new IllegalArgumentException("No enum constant with conf name:" + confName);
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public String getName() {
        return name;
    }

    public String getConfName() {
        return confName;
    }


    public EComponentScheduleType getComponentScheduleType() {
        return componentScheduleType;
    }

    // hadoop引擎组件
    private static List<EComponentType> HadoopComponents = Lists.newArrayList(
            EComponentType.SPARK, EComponentType.SPARK_THRIFT,
            EComponentType.FLINK, EComponentType.HIVE_SERVER,
            EComponentType.YARN
    );

    public static MultiEngineType getEngineTypeByComponent(EComponentType componentType, Integer deployType) {
        if (HadoopComponents.contains(componentType)) {
            return MultiEngineType.HADOOP;
        }
        return null;
    }

    // 需要添加TypeName的组件
    public static List<EComponentType> typeComponentVersion = Lists.newArrayList(
            EComponentType.FLINK, EComponentType.SPARK,
            EComponentType.HDFS, EComponentType.HIVE_SERVER
    );

    //允许一个组件多个版本
    public static List<EComponentType> multiVersionComponents = Lists.newArrayList(EComponentType.FLINK, EComponentType.SPARK);

    //没有控件渲染的组件
    public static List<EComponentType> noControlComponents = Lists.newArrayList(EComponentType.YARN, EComponentType.HDFS);

}

