### UML



<div hidden>

```
@startuml uml

class Email {
    String email
}

class User {
    String firstName
    String lastName
    String password
    String phone
}

User "1" - "1" Email 

class Admin {

}

class Student {
     
}


class Company {
    String companyName
    String companyAddress
}

User <|-- Student 
User <|-- Company
User <|-- Admin

Company "1" *-- "0..*" Review
Student "0..*" -- "0..*" Company
(Student, Company) .. Like
class Like {
    boolean isLiked
}


class Review {
    String reviewerName
    Enum Badge
    String content
}

Student "1" o-- "0..*" Review

class InterviewReview {
    String InterviewQuestion

}

class WorkReview {
    String JobTitle
}

enum Badge {
    AWESOME
    GOOD
    MEDIOCORE
    BAD
}
Review <|-- InterviewReview
Review <|-- WorkReview
@enduml

```
</div>


![uml.svg](./resources/uml.svg)
