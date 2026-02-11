package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_inquiries")
@Data
@NoArgsConstructor
public class ContactInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime receivedAt;

    @PrePersist
    protected void onCreate() {
        this.receivedAt = LocalDateTime.now();
        this.status = "NEW"; // Default status

        // Subject ke basis pe Type set kar sakte hain (example)
        if (this.subject != null) {
            if (this.subject.equalsIgnoreCase("Partnership Inquiry")) {
                this.type = "partnership";
            } else if (this.subject.equalsIgnoreCase("Support Request")) {
                this.type = "support";
            } else {
                this.type = "general";
            }
        } else {
            this.type = "general";
        }
    }
}