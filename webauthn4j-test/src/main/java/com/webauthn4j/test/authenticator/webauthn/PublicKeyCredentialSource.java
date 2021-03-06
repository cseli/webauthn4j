/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.test.authenticator.webauthn;

import com.webauthn4j.data.PublicKeyCredentialType;
import com.webauthn4j.data.attestation.authenticator.COSEKey;

public class PublicKeyCredentialSource {

    private PublicKeyCredentialType type;
    private byte[] id;
    private COSEKey privateKey;
    private String rpId;
    private byte[] userHandle;
    private Object otherUI;

    public PublicKeyCredentialType getType() {
        return type;
    }

    public void setType(PublicKeyCredentialType type) {
        this.type = type;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public COSEKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(COSEKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public byte[] getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(byte[] userHandle) {
        this.userHandle = userHandle;
    }

    public Object getOtherUI() {
        return otherUI;
    }

    public void setOtherUI(Object otherUI) {
        this.otherUI = otherUI;
    }
}
