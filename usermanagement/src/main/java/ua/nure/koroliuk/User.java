package main.java.ua.nure.koroliuk;

import java.util.Calendar;
import java.util.Date;


public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User() {

    }

    public User(String firstName, String lastName, Date now) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = now;
    }

    public User(Long id, String firstName, String lastName, Date now) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = now;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() throws IllegalStateException {
        if (getFirstName() == null || getLastName() == null) {
            throw new IllegalStateException();
        }
        StringBuilder fullName = new StringBuilder();
        fullName.append(getLastName()).append(", ").append(getFirstName());
        return fullName.toString();
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(dateOfBirth);
        int year = calendar.get(Calendar.YEAR);
        return currentYear - year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.getId().equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        if (this.getId() == null) {
            return 0;
        }
        return this.getId().hashCode();
    }
}
