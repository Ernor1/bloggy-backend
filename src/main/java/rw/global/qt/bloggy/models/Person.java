package rw.global.qt.bloggy.models; /**
 *
 */


import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;
import lombok.NoArgsConstructor;
import rw.global.qt.bloggy.audits.Initializer;
import rw.global.qt.bloggy.enums.EGender;
import rw.global.qt.bloggy.enums.EVisibility;


import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class Person extends Initializer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;


    @Column(name = "dob")
    private LocalDate dob;

    private EGender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "national_id")
    private String nationalId;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private EVisibility visibility = EVisibility.VISIBLE;



    public Person(String firstName, String lastName, String email, LocalDate dob, EGender gender, String phoneNumber, String nationalId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.nationalId = nationalId;
    }
    //private User (After user creation)
}