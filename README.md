# Big Five Personality Test - AI-Powered System

## 📌 Overview
This project is a **Big Five Personality Test** application built with **Angular 19** (frontend) and **Spring Boot** (backend). It allows users to answer a set of questions, submits their responses to an AI-powered backend, and receives a personality analysis based on the **Big Five Personality Traits**.

---

## 🚀 Technologies Used

### **Backend (Spring Boot + Spring AI)**
- **Spring Boot 3**: Framework for building RESTful APIs.
- **Spring AI**: Used to integrate AI models for analyzing personality responses.
- **Spring Data JPA**: ORM for database management.
- **MySQL**: Database to store questions and responses.
- **OpenAI API**: Generates personality analysis based on responses.

### **Frontend (Angular 19)**
- **Angular 19 Standalone Components**: Lightweight structure for better performance.
- **Angular HTTPClient**: Communicates with the backend API.
- **Bootstrap**: Used for styling and UI components.

---

## 📖 The Big Five Personality Traits

The **Big Five** personality model categorizes human personality into five dimensions:

1. **Openness to Experience** 🌍
   - Creativity, curiosity, and willingness to try new experiences.
   - High score: Imaginative, open-minded.
   - Low score: Prefers routine, traditional.

2. **Conscientiousness** 📋
   - Organization, responsibility, and dependability.
   - High score: Hardworking, disciplined.
   - Low score: Impulsive, less organized.

3. **Extraversion** 🎉
   - Sociability, assertiveness, and energy.
   - High score: Outgoing, energetic.
   - Low score: Reserved, prefers solitude.

4. **Agreeableness** 🤝
   - Compassion, cooperation, and trust.
   - High score: Friendly, empathetic.
   - Low score: Competitive, skeptical.

5. **Neuroticism** 😟
   - Emotional stability and tendency to experience negative emotions.
   - High score: Prone to stress, anxious.
   - Low score: Emotionally stable, resilient.


---

## 📡 System Architecture

The system is designed as a **client-server model**, where the **Angular frontend** communicates with the **Spring Boot backend**.

### **Workflow**
1. **User loads the test**: Angular fetches questions from the backend (`GET /api/question`).
2. **User answers each question**: Responses are stored locally.
3. **Submission**: Once all responses are collected, they are sent to the backend (`POST /api/person`).
4. **AI Analysis**: The backend processes the data and generates a personality profile using OpenAI API.
5. **Results Displayed**: The frontend receives the response and presents a personality summary.


---

## 📂 Backend Components (Spring Boot + Spring AI)

### **1️⃣ Model (JPA Entity)**
```java
@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
}
```

### **2️⃣ Repository (Spring Data JPA)**
```java
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {}
```

### **3️⃣ Service (Business Logic)**
```java
@Service
public class QuestionService {
    private final QuestionRepository repository;
    public List<Question> getAllQuestions() { return repository.findAll(); }
}
```

### **4️⃣ Controller (REST API)**
```java
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @GetMapping
    public List<Question> getQuestions() {
        return questionService.getAllQuestions();
    }
}
```

### **5️⃣ AI-Powered Personality Analysis**
```java
@Service
public class PersonalityService {
    private final ChatClient chatClient;
    public PersonalityAnalysis analyze(List<UserResponse> responses) {
        String systemMessage = "Analyze the user's personality based on their responses...";
        return chatClient.prompt().system(systemMessage).user(responses).call().entity(PersonalityAnalysis.class);
    }
}
```

---

## 🎨 Frontend Components (Angular 19)

### **1️⃣ Model**
```typescript
export interface Question {
  id: number;
  content: string;
}
```

### **2️⃣ Service (HTTP Calls)**
```typescript
@Injectable({ providedIn: 'root' })
export class QuestionService {
  private baseUrl = 'http://localhost:8080/api/question';
  constructor(private http: HttpClient) {}
  getAll(): Observable<Question[]> {
    return this.http.get<Question[]>(this.baseUrl);
  }
}
```

### **3️⃣ Component (Display Questions)**
```typescript
@Component({
  selector: 'app-questions-list',
  standalone: true,
  templateUrl: './questions-list.component.html',
})
export class QuestionsListComponent implements OnInit {
  questions: Question[] = [];
  constructor(private questionService: QuestionService) {}
  ngOnInit(): void {
    this.questionService.getAll().subscribe(data => this.questions = data);
  }
}
```

### **4️⃣ HTML Template (List of Questions with Inputs)**
```html
<div class="container">
  <h2>Big Five Personality Test</h2>
  <ul>
    @for (question of questions; track question.id) {
      <li>{{ question.content }}
        <input type="radio" name="q{{question.id}}" value="1"> Strongly Disagree
        <input type="radio" name="q{{question.id}}" value="5"> Strongly Agree
      </li>
    }
  </ul>
  <button (click)="submitResponses()">Submit</button>
</div>
```

---

## 🔥 How to Run the Project

### **1️⃣ Backend (Spring Boot)**
```sh
cd backend
mvn spring-boot:run
```

### **2️⃣ Frontend (Angular 19)**
```sh
cd frontend
npm install
ng serve
```

---

## 📌 Conclusion
This project successfully integrates **AI-powered personality analysis** into a **Spring Boot + Angular application**. It provides real-time insights based on the **Big Five Personality Traits**. Future improvements can include:
- Enhancing the AI model for deeper analysis.
- Adding user authentication for personalized reports.
- Storing past results for progress tracking.


