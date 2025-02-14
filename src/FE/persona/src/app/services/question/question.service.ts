import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Question} from '../../models/question.model';


const baseUrl = 'http://localhost:8080/api/question';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(private http : HttpClient) { }

  getAll(): Observable<Question[]> {
    return this.http.get<Question[]>(baseUrl);
  }

  get(id: any): Observable<Question> {
    return this.http.get<Question>(`${baseUrl}/${id}`);
  }

}
