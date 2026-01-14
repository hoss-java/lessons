@FunctionalInterface
public interface PersonRule {
    boolean test(Person person);
}

---
PersonRule isActive = person -> person.isActive();
PersonRule isAdult = person -> person.getAge() >= 18;
PersonRule livesInStockholm = person -> person.getCity().equals("Stockholm");

---
public List<Person> filterPeople(List<Person> people, PersonRule rule) {
    List<Person> result = new ArrayList<>();
    for (Person person : people) {
        if (rule.test(person)) {
            result.add(person);
        }
    }
    return result;
}
---
PersonRule isActiveAndAdult = person -> isActive.test(person) && isAdult.test(person);
PersonRule isAdultOrLivesInStockholm = person -> isAdult.test(person) || livesInStockholm.test(person);
PersonRule isNotActive = person -> !isActive.test(person);

---

public class PersonProcessor {
    public List<Person> findPeople(List<Person> people, PersonRule rule) {
        List<Person> result = new ArrayList<>();
        for (Person person : people) {
            if (rule.test(person)) {
                result.add(person);
            }
        }
        return result;
    }

    public void applyToMatching(List<Person> people, PersonRule rule, PersonAction action) {
        for (Person person : people) {
            if (rule.test(person)) {
                action.perform(person);
            }
        }
    }
}

---
Predicate<Person> isActive = person -> person.isActive();
Predicate<Person> isAdult = person -> person.getAge() >= 18;
Predicate<Person> livesInStockholm = person -> person.getCity().equals("Stockholm");

---

Consumer<Person> printName = person -> System.out.println(person.getName());
Consumer<Person> sendEmail = person -> System.out.println("Send email to " + person.getName());

