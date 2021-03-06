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

package com.webauthn4j.data.extension.authenticator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.webauthn4j.converter.jackson.deserializer.CredentialProtectionPolicyByteDeserializer;
import com.webauthn4j.converter.jackson.serializer.CredentialProtectionPolicyByteSerializer;
import com.webauthn4j.data.extension.CredentialProtectionPolicy;
import com.webauthn4j.util.AssertUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;
import java.util.*;

/**
 * {@link AuthenticationExtensionsAuthenticatorInputs} containing the authenticator extension input values for
 * zero or more WebAuthn extensions, as defined in §9 WebAuthn Extensions.
 *
 * @see <a href="https://www.w3.org/TR/webauthn-1/#typedefdef-authenticationextensionsauthenticatorinputs">
 * §5.9. Authentication Extensions Authenticator Inputs (typedef AuthenticationExtensionsAuthenticatorInputs)</a>
 */
public class AuthenticationExtensionsAuthenticatorInputs<T extends ExtensionAuthenticatorInput> implements Serializable {

    @JsonIgnore
    private final Map<String, Serializable> unknowns = new HashMap<>();
    @JsonProperty
    private Boolean uvm;
    @JsonSerialize(using = CredentialProtectionPolicyByteSerializer.class)
    @JsonDeserialize(using = CredentialProtectionPolicyByteDeserializer.class)
    @JsonProperty
    private CredentialProtectionPolicy credProtect;
    @JsonIgnore
    private Map<Class<? extends T>, T> extensions;

    @JsonAnyGetter
    private Map<String, Serializable> getUnknowns() {
        return Collections.unmodifiableMap(this.unknowns);
    }

    @JsonAnySetter
    private void setUnknowns(@NonNull String name, @Nullable Serializable value) {
        this.unknowns.put(name, value);
    }

    @JsonIgnore
    public @NonNull Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        if (uvm != null) {
            keys.add("uvm");
        }
        if (credProtect != null) {
            keys.add("credProtect");
        }
        keys.addAll(getUnknownKeys());
        return keys;
    }

    @JsonIgnore
    public @NonNull Set<String> getUnknownKeys() {
        return unknowns.keySet();
    }

    @JsonIgnore
    public @Nullable Object getValue(String key) {
        switch (key) {
            case "uvm":
                return uvm;
            case "credProtect":
                return credProtect;
            default:
                return unknowns.get(key);
        }
    }

    @JsonIgnore
    public @Nullable Boolean getUvm() {
        return this.uvm;
    }

    @JsonIgnore
    public @Nullable CredentialProtectionPolicy getCredProtect() {
        return this.credProtect;
    }

    @SuppressWarnings("unchecked")
    public @Nullable <E extends T> E getExtension(@NonNull Class<E> tClass) {
        return (E) getExtensions().get(tClass);
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    private @NonNull Map<Class<? extends T>, T> getExtensions() {
        if (extensions == null) {
            Map<Class<? extends T>, T> map = new HashMap<>();
            if (uvm != null) {
                map.put((Class<? extends T>) UserVerificationMethodExtensionAuthenticatorInput.class, (T) new UserVerificationMethodExtensionAuthenticatorInput(uvm));
            }
            if (credProtect != null) {
                map.put((Class<? extends T>) CredentialProtectionExtensionAuthenticatorInput.class, (T) new CredentialProtectionExtensionAuthenticatorInput(credProtect));
            }
            extensions = Collections.unmodifiableMap(map);
        }
        return extensions;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationExtensionsAuthenticatorInputs<?> that = (AuthenticationExtensionsAuthenticatorInputs<?>) o;
        return Objects.equals(uvm, that.uvm) &&
                Objects.equals(credProtect, that.credProtect) &&
                Objects.equals(unknowns, that.unknowns) &&
                Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uvm, credProtect, unknowns, extensions);
    }

    public static class BuilderForRegistration {

        private final Map<String, Serializable> unknowns = new HashMap<>();
        private Boolean uvm;
        private CredentialProtectionPolicy credProtect;

        public @NonNull AuthenticationExtensionsAuthenticatorInputs<RegistrationExtensionAuthenticatorInput> build() {
            AuthenticationExtensionsAuthenticatorInputs<RegistrationExtensionAuthenticatorInput> instance = new AuthenticationExtensionsAuthenticatorInputs<>();
            instance.uvm = this.uvm;
            instance.credProtect = this.credProtect;
            instance.unknowns.putAll(this.unknowns);

            return instance;
        }

        public @NonNull BuilderForRegistration setUvm(@Nullable Boolean uvm) {
            this.uvm = uvm;
            return this;
        }

        public @NonNull BuilderForRegistration setCredProtect(@Nullable CredentialProtectionPolicy credProtect) {
            this.credProtect = credProtect;
            return this;
        }

        public @NonNull BuilderForRegistration set(@NonNull String key, @Nullable Serializable value) {
            AssertUtil.notNull(key, "key must not be null.");
            AssertUtil.notNull(value, "value must not be null.");
            unknowns.put(key, value);
            return this;
        }

    }

    public static class BuilderForAuthentication {

        private final Map<String, Serializable> unknowns = new HashMap<>();
        private Boolean uvm;

        public @NonNull AuthenticationExtensionsAuthenticatorInputs<AuthenticationExtensionAuthenticatorInput> build() {
            AuthenticationExtensionsAuthenticatorInputs<AuthenticationExtensionAuthenticatorInput> instance = new AuthenticationExtensionsAuthenticatorInputs<>();
            instance.uvm = this.uvm;
            instance.unknowns.putAll(this.unknowns);

            return instance;
        }

        public @NonNull BuilderForAuthentication setUvm(@Nullable Boolean uvm) {
            this.uvm = uvm;
            return this;
        }

        public @NonNull BuilderForAuthentication set(@NonNull String key, @Nullable Serializable value) {
            AssertUtil.notNull(key, "key must not be null.");
            AssertUtil.notNull(value, "value must not be null.");
            unknowns.put(key, value);
            return this;
        }


    }


}