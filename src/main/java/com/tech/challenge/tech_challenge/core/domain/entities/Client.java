package com.tech.challenge.tech_challenge.core.domain.entities;

import com.tech.challenge.tech_challenge.core.application.exceptions.ClientMustHaveNameAndEmailOrValidCPFException;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidEmailAddress;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.application.util.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
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

    public void validate() {
        if (StringUtils.isEmpty(this.cpf) && (StringUtils.isEmpty(this.name) || StringUtils.isEmpty(this.email))) {
            throw new ClientMustHaveNameAndEmailOrValidCPFException();
        }

        if (!StringUtils.isEmpty(this.cpf)) {
            if (!CPFValidator.isCPF(this.cpf)) {
                throw new InvalidClientCPF(this.cpf);
            }
            this.cpf = CPFValidator.formatCPF(this.cpf);
        }

        if (!StringUtils.isEmpty(this.email) && !EmailValidator.isValidEmail(this.email)) {
            throw new InvalidEmailAddress(this.email);
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