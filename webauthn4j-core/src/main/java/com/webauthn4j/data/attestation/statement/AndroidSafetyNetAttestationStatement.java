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

package com.webauthn4j.data.attestation.statement;

import com.fasterxml.jackson.annotation.*;
import com.webauthn4j.data.jws.JWS;
import com.webauthn4j.util.AssertUtil;
import com.webauthn4j.validator.exception.ConstraintViolationException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonTypeName(AndroidSafetyNetAttestationStatement.FORMAT)
public class AndroidSafetyNetAttestationStatement implements CertificateBaseAttestationStatement {

    public static final String FORMAT = "android-safetynet";

    private final String ver;
    private final JWS<Response> response;

    @JsonCreator
    public AndroidSafetyNetAttestationStatement(
            @NonNull @JsonProperty("ver") String ver,
            @NonNull @JsonProperty("response") JWS<Response> response) {
        AssertUtil.notNull(ver, "ver must not be null");
        AssertUtil.notNull(response, "response must not be null");
        this.ver = ver;
        this.response = response;
    }

    @JsonIgnore
    @Override
    public @NonNull String getFormat() {
        return FORMAT;
    }

    @JsonIgnore
    @Override
    public @Nullable AttestationCertificatePath getX5c() {
        JWS<Response> res = getResponse();
        CertPath x5c = res.getHeader().getX5c();
        if (x5c == null) {
            return null;
        }
        return new AttestationCertificatePath(x5c.getCertificates().stream().map(X509Certificate.class::cast).collect(Collectors.toList()));
    }

    @Override
    public void validate() {
        CertPath x5c = getResponse().getHeader().getX5c();
        if (x5c == null || x5c.getCertificates().isEmpty()) {
            throw new ConstraintViolationException("No attestation certificate is found in android safetynet attestation statement.");
        }
    }

    @JsonGetter("ver")
    public @NonNull String getVer() {
        return ver;
    }

    @JsonGetter("response")
    public @NonNull JWS<Response> getResponse() {
        return response;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidSafetyNetAttestationStatement that = (AndroidSafetyNetAttestationStatement) o;
        return Objects.equals(ver, that.ver) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ver, response);
    }

    @Override
    public String toString() {
        return "AndroidSafetyNetAttestationStatement(" +
                "ver=" + ver +
                ", response=" + response +
                ')';
    }
}
