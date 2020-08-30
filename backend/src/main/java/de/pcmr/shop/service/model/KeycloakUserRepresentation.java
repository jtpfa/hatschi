package de.pcmr.shop.service.model;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.Objects;

public class KeycloakUserRepresentation extends UserRepresentation {

    public KeycloakUserRepresentation(UserRepresentation userRepresentation) {
        super.setAccess(userRepresentation.getAccess());
        super.setAttributes(userRepresentation.getAttributes());
        super.setClientConsents(userRepresentation.getClientConsents());
        super.setClientRoles(userRepresentation.getClientRoles());
        super.setCreatedTimestamp(userRepresentation.getCreatedTimestamp());
        super.setCredentials(userRepresentation.getCredentials());
        super.setDisableableCredentialTypes(userRepresentation.getDisableableCredentialTypes());
        super.setEmail(userRepresentation.getEmail());
        super.setEmailVerified(userRepresentation.isEmailVerified());
        super.setEnabled(userRepresentation.isEnabled());
        super.setFederatedIdentities(userRepresentation.getFederatedIdentities());
        super.setFederationLink(userRepresentation.getFederationLink());
        super.setFirstName(userRepresentation.getFirstName());
        super.setGroups(userRepresentation.getGroups());
        super.setId(userRepresentation.getId());
        super.setLastName(userRepresentation.getLastName());
        super.setNotBefore(userRepresentation.getNotBefore());
        super.setOrigin(userRepresentation.getOrigin());
        super.setRealmRoles(userRepresentation.getRealmRoles());
        super.setRequiredActions(userRepresentation.getRequiredActions());
        super.setSelf(userRepresentation.getSelf());
        super.setServiceAccountClientId(userRepresentation.getServiceAccountClientId());
        super.setSocialLinks(userRepresentation.getSocialLinks());
        super.setUsername(userRepresentation.getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UserRepresentation)) return false;
        UserRepresentation userRepresentation = (UserRepresentation) obj;
        return Objects.equals(getEmail(), userRepresentation.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
