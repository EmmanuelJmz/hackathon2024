package models;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
    @Table(name = "users")
    @EntityListeners(AuditingEntityListener.class)
    public class User {
        @Id
        @Column(name = "id")
        private int id;

        @Column(name = "name")
        private String userName;

        @Column(name = "status")
        private String status;

        public User() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

