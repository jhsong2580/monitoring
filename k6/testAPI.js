import http from 'k6/http';
import { check, group, sleep, fail } from 'k6';

const BASE_URL = 'http://211.184.188.33:19080';

function sleepAPI(){
  var params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  let sleepRes = http.get(`${BASE_URL}/api/sleep`, null, params);
  check(sleepRes, {
    'sleep Success': (resp) => resp.status === 200,
  });
}

function databaseAPI(){
  var params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  let databaseRes = http.get(`${BASE_URL}/api/database`, null, params);
  check(databaseRes, {
    'database Success': (resp) => resp.status === 200,
  });
}

function imageAPI(){
  var params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  let databaseRes = http.get(`${BASE_URL}/api/image`, null, params);
  check(databaseRes, {
    'image Success': (resp) => resp.status === 200,
  });
}
function getBigImages(){
  var params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  let bigImageAccessRes1 = http.get(`${BASE_URL}/1.jpeg`, null, params);
  let bigImageAccessRes2 = http.get(`${BASE_URL}/2.jpeg`, null, params);
  let bigImageAccessRes3 = http.get(`${BASE_URL}/3.jpeg`, null, params);
  let bigImageAccessRes4 = http.get(`${BASE_URL}/4.jpeg`, null, params);
  let bigImageAccessRes5 = http.get(`${BASE_URL}/5.jpg`, null, params);
  let bigImageAccessRes6 = http.get(`${BASE_URL}/6.jpg`, null, params);

  checkRes200(bigImageAccessRes1, "bigImageAccess1_success");
  checkRes200(bigImageAccessRes2, "bigImageAccess2_success");
  checkRes200(bigImageAccessRes3, "bigImageAccess3_success");
  checkRes200(bigImageAccessRes4, "bigImageAccess4_success");
  checkRes200(bigImageAccessRes5, "bigImageAccess5_success");
  checkRes200(bigImageAccessRes6, "bigImageAccess6_success");
}

function checkRes200(response, successMessage){
  check(response, {
    successMessage: (resp) => resp.status === 200,
  });
}

export {getBigImages};