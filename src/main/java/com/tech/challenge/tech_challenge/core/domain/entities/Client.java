package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientName;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidEmailAddress;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.application.util.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String cpf;

    @Column
    private String email;

    public void validate() throws ValidationException {
        if (StringUtils.isEmpty(cpf) || !CPFValidator.isCPF(this.cpf)) {
            throw new InvalidClientCPF(cpf);
        }
        this.cpf = CPFValidator.formatCPF(this.cpf);

        if (StringUtils.isEmpty(this.email) && !EmailValidator.isValidEmail(this.email)) {
            throw new InvalidEmailAddress(this.email);
        }

        if (StringUtils.isEmpty(name)) {
            throw new InvalidClientName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client )) return false;
        return id != null && id.equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 