# 부하 및 부하에 대한 개선 + 모니터링

---
# 어플리케이션 시나리오(module-application)
- 3초가 소요되는 API Call
    - /api/sleep
- Database를 통해 가져오는 데이터가 1000건 이상인 API Call
    - /api/database
- 용량이 큰 이미지를 가져오는 Api Call
    - /api/image
---
# 부하
- k6

--- 
# Monitoring (module-monitoring)
- grafana
- spring-boot-starter-actuator
---
# 성능 개선 요소
- 웹캐시
- Redis를 통한 DB 접근 최소화
- 수평 확장
- 이미지 압축

---
