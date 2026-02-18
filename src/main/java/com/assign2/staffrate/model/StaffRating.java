package com.assign2.staffrate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;


@Entity
@Table(
    name = "staff_ratings",
    uniqueConstraints = @UniqueConstraint(columnNames = "email")
)


public class StaffRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 60, message = "Name must be between 2 and 60 characters")
    @Column(nullable= false)
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "must be a valid email")
    @Column(nullable=false , unique=true )

    private String email;

    @NotNull(message = "Role type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffRoleType roleType;


    @NotNull(message = "Clarity score required")
    @Min(value = 1, message = "Must be between 1 and 10")
    @Max(value = 10, message = "Must be between 1 and 10")

    private Integer clarity;

    
    @NotNull(message = "Niceness score required")
    @Min(value = 1, message = "Must be between 1 and 10")
    @Max(value = 10, message = "Must be between 1 and 10")

    private Integer niceness;

    
    @NotNull(message = "Knowledagble score required")
    @Min(value = 1, message = "Must be between 1 and 10")
    @Max(value = 10, message = "Must be between 1 and 10")

    private Integer knowledgable;

  @Size(max = 300, message = "Comment must be at most 300 characters")

    private String comment;

    

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist

    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

        @PreUpdate

    protected void onUdpate(){
        updatedAt = LocalDateTime.now();
    }

    @Transient
    public double getOverallScore(){

        if(clarity == null || niceness == null || knowledgable == null){
            return 0.0;
        }else{
            int sum = (clarity + niceness + knowledgable ) / 3;
            return sum;
        }
    }

    public Long getId() { return id; }
    
    
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public StaffRoleType getRoleType() { return roleType; }

    public void setRoleType(StaffRoleType roleType) { this.roleType = roleType; }

    public Integer getClarity() { return clarity; }

    public void setClarity(Integer clarity) { this.clarity = clarity; }

    public Integer getNiceness() { return niceness; }

    public void setNiceness(Integer niceness) { this.niceness = niceness; }

    public Integer getKnowledgeableScore() { return knowledgable; }

    public void setKnowledgeableScore(Integer knowledgable) { this.knowledgable = knowledgable; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }


}



