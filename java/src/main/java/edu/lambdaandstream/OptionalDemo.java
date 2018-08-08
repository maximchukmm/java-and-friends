package edu.lambdaandstream;

import java.util.Optional;

class OptionalDemo {
    public static void main(String[] args) {
        PersonAddressStreet personAddressStreet = new PersonAddressStreet("STREET");
        PersonAddress personAddress = new PersonAddress(personAddressStreet);

        PersonDemo person = new PersonDemo();
        person.setAddress(personAddress);
        person.setFirstName("Demo");

        Optional<PersonDemo> optionalPerson = Optional.of(person);

        Optional<Optional<PersonAddress>> personAddress1 = optionalPerson
            .map(PersonDemo::getAddress);

        Optional<String> optionalStreet = optionalPerson
            .flatMap(PersonDemo::getAddress)
            .flatMap(PersonAddress::getPersonAddressStreet)
            .map(PersonAddressStreet::getStreet);

        String street = optionalPerson
            .flatMap(PersonDemo::getAddress)
            .flatMap(PersonAddress::getPersonAddressStreet)
            .map(PersonAddressStreet::getStreet)
            .orElse("EMPTY");

        System.out.println("optional street: " + optionalStreet);
        System.out.println("street: " + street);
    }
}

class PersonDemo {

    private Optional<String> firstName;

    private Optional<String> secondName;

    private Optional<Integer> age;

    private Optional<PersonAddress> address;

    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Optional.ofNullable(firstName);
    }

    public Optional<String> getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = Optional.of(secondName);
    }

    public Optional<Integer> getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = Optional.ofNullable(age);
    }

    public Optional<PersonAddress> getAddress() {
        return address;
    }

    public void setAddress(PersonAddress address) {
        this.address = Optional.of(address);
    }
}

class PersonAddress {
    private Optional<PersonAddressStreet> personAddressStreet;

    public PersonAddress(PersonAddressStreet personAddressStreet) {
        this.personAddressStreet = Optional.of(personAddressStreet);
    }

    public Optional<PersonAddressStreet> getPersonAddressStreet() {
        return personAddressStreet;
    }

    public void setPersonAddressStreet(PersonAddressStreet personAddressStreet) {
        this.personAddressStreet = Optional.of(personAddressStreet);
    }
}

class PersonAddressStreet {
    private String street;

    public PersonAddressStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
