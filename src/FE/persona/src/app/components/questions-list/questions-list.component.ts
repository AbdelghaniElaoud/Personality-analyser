import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Question } from '../../models/question.model';
import { Response } from '../../models/response.model';
import { PersonalityResult } from '../../models/personality-result.model';
import { QuestionService } from '../../services/question/question.service';
import { ResponseService } from '../../services/response/response.service';

@Component({
  selector: 'app-questions-list',
  templateUrl: './questions-list.component.html',
  styleUrls: ['./questions-list.component.css'],
  imports: [CommonModule, FormsModule],
  standalone: true
})
export class QuestionsListComponent implements OnInit {
  questions?: Question[];
  responses: Map<number, number> = new Map();
  personalityResult?: PersonalityResult;
  submitted = false;
  currentPage = 0;
  questionsPerPage = 10;
  isLoading = false;

  constructor(
    private questionService: QuestionService,
    private responseService: ResponseService
  ) {}

  ngOnInit(): void {
    this.retrieveQuestions();
  }

  retrieveQuestions(): void {
    this.questionService.getAll()
      .subscribe({
        next: (data) => {
          this.questions = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  get currentQuestions(): Question[] {
    if (!this.questions) return [];
    const start = this.currentPage * this.questionsPerPage;
    return this.questions.slice(start, start + this.questionsPerPage);
  }

  get totalPages(): number {
    return this.questions ? Math.ceil(this.questions.length / this.questionsPerPage) : 0;
  }

  get progress(): number {
    if (!this.questions) return 0;
    return (this.responses.size / this.questions.length) * 100;
  }

  onResponseChange(questionId: number, value: number): void {
    this.responses.set(questionId, value);
  }

  isQuestionAnswered(questionId: number): boolean {
    return this.responses.has(questionId);
  }

  canGoNext(): boolean {
    return this.currentQuestions.every(q => this.isQuestionAnswered(q.id));
  }

  nextPage(): void {
    if (this.canGoNext() && this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      window.scrollTo(0, 0);
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      window.scrollTo(0, 0);
    }
  }

  submitResponses(): void {
    if (!this.questions || this.responses.size !== this.questions.length) {
      alert('Please answer all questions before submitting.');
      return;
    }

    this.isLoading = true;  // Set loading to true before API call

    const responsesArray: Response[] = Array.from(this.responses.entries()).map(([questionId, response]) => ({
      questionId,
      response
    }));

    this.responseService.sendResponses(responsesArray).subscribe({
      next: (result) => {
        this.personalityResult = result;
        this.submitted = true;
        window.scrollTo(0, 0);
        this.isLoading = false;
      },
      error: (e) => {
        console.error(e);
        alert('An error occurred while submitting your responses.');
        this.isLoading = false;
      }
    });
  }
}
