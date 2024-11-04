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

    public void validate() throws ValidationException {
        boolean hasName = !Objects.isNull(this.name);
        boolean hasEmail = !Objects.isNull(this.email);
        boolean hasCPF = !Objects.isNull(this.cpf);
        
        if (!hasCPF && !(hasName && hasEmail)) {
            throw new ClientMustHaveNameAndEmailOrValidCPFException();
        }

        boolean hasValidCPF = CPFValidator.isCPF(this.cpf);
        if (!hasValidCPF && hasCPF) {
            throw new InvalidClientCPF(this.cpf);
        }

        boolean hasValidEmail = EmailValidator.isValidEmail(this.email);
        if(!hasValidEmail && hasEmail){
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