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
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.ambari.logsearch.handler;

import org.apache.ambari.logsearch.conf.SolrPropsConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.SolrException;

import java.util.ArrayList;
import java.util.List;

public class ListCollectionHandler implements SolrZkRequestHandler<List<String>> {

  private static final Logger logger = LogManager.getLogger(ListCollectionHandler.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<String> handle(CloudSolrClient solrClient, SolrPropsConfig solrPropsConfig) throws Exception {
    try {
      CollectionAdminRequest.List colListReq = new CollectionAdminRequest.List();
      CollectionAdminResponse response = colListReq.process(solrClient);
      if (response.getStatus() != 0) {
        logger.error("Error getting collection list from solr.  response=" + response);
        return null;
      }
      return (List<String>) response.getResponse().get("collections");
    } catch (SolrException e) {
      logger.error("getCollections() operation failed", e);
      return new ArrayList<>();
    }
  }
}
