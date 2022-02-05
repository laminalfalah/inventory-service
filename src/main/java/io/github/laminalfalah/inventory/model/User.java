package io.github.laminalfalah.inventory.model;

/*
 * Copyright (C) 2022 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.inventory.model
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author laminalfalah on 25/01/22
 */

@Setter
@Getter
@SuperBuilder
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends AuditTrail {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "verify", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean verify;

    @Column(name = "token")
    private String token;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

}
