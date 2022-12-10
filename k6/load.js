import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';
import 'testAPI';
import * as apis from './testAPI.js';
export let options = {
  stages: [
    { duration: '10s', target: 10 },
    { duration: '1m', target: 30 },
    { duration: '1m', target: 10 },
    { duration: '30s', target: 0 },
  ],

  thresholds: {
    http_req_duration: ['p(99)<1500'], // 66% of requests must complete below 1.5s , sleep api 는 3초 이내이다
  },
};



export default function ()  {
  sleepAPI();

  databaseAPI();

  imageAPI();

  sleep(1);
};

