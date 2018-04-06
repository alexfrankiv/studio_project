package model;

import java.util.Comparator;

public class Musician {

    private long id;
    private String name;
    private String lastName;
    private String phone;
    private Double rating;

    public Musician(long id, String name, String lastName, String phone, Double rate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.rating = rate;
    }

    public Musician() {
    }

    @Override
    public String toString() {
        return name + ' ' + lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musician musician = (Musician) o;
        return id == musician.id;
    }

    //utilities
    public String getFullName() {
        return name + " " + lastName;
    }

    public static Comparator<Musician> musComparator = new Comparator<Musician>() {

        public int compare(Musician s1, Musician s2) {
            long firstId = s1.getId();
            long secondId = s2.getId();
            return (int)(firstId-secondId);

        }
    };

}
