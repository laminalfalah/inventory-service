package io.github.laminalfalah.inventory.properties;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.properties
 *
 * This is part of the inventory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.laminalfalah.inventory.payload.Direction;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author laminalfalah on 30/01/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("inventory.paging")
public class PagingProperties {

    private Long defaultPage = 1L;

    private Long defaultSize = 1000L;

    private Long maxSizePerPage = 1000L;

    private Direction defaultDirection = Direction.ASC;

    private String defaultField;

    private Direction defaultFieldDirection;

    private Query query = new Query();

    private Boolean logEnabled = false;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Query implements Serializable {

        private String pageKey = "page";

        private String sizeKey = "size";

        private String sortKey = "sort";

    }

}
