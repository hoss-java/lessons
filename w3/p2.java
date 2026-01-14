List<Person> activePeople = people.stream()
        .filter(Person::isActive)
        .collect(Collectors.toList());
---
List<String> names = people.stream()
        .map(Person::getName)
        .collect(Collectors.toList());
---
long adultCount = people.stream()
        .filter(person -> person.getAge() >= 18)
        .count();
---
List<Person> sortedPeople = people.stream()
        .sorted(Comparator.comparingInt(Person::getAge))
        .collect(Collectors.toList());
---
Person firstActiveInStockholm = people.stream()
        .filter(person -> person.isActive() && person.getCity().equals("Stockholm"))
        .findFirst()
        .orElse(null);
---
List<Person> activeAdults = people.stream()
        .filter(Person::isActive)
        .filter(person -> person.getAge() >= 18)
        .collect(Collectors.toList());

List<Person> activeInStockholm = people.stream()
        .filter(Person::isActive)
        .filter(person -> person.getCity().equals("Stockholm"))
        .collect(Collectors.toList());

List<Person> inactiveOver30 = people.stream()
        .filter(person -> !person.isActive())
        .filter(person -> person.getAge() > 30)
        .collect(Collectors.toList());
---
List<String> uniqueCities = people.stream()
        .map(Person::getCity)
        .distinct()
        .sorted()
        .collect(Collectors.toList());

List<Character> uniqueFirstLetters = people.stream()
        .map(person -> person.getName().charAt(0))
        .distinct()
        .sorted()
        .collect(Collectors.toList());


---
List<String> formattedPeople = people.stream()
        .map(person -> person.getName() + " (" + person.getAge() + ") - " + person.getCity())
        .collect(Collectors.toList());

List<String> emails = people.stream()
        .map(person -> person.getName().toLowerCase() + "@example.com")
        .collect(Collectors.toList());
