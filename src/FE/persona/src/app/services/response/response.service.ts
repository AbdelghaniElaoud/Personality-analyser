import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Response} from '../../models/response.model';
import {Observable} from 'rxjs';

const baseUrl = 'http://localhost:8080/api/person';

@Injectable({
  providedIn: 'root'
})
export class ResponseService {

  constructor(private http: HttpClient) {}

  sendResponses(responses: Response[]): Observable<any> {
    console.log(responses)
    return this.http.post<any>(baseUrl, responses);
  }
}
