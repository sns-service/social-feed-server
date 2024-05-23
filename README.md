## Description
- 소셜 피드 서버입니다.
- EKS(쿠버네티스) sns cluster 내부에 존재합니다.
<br>

## 역할 ✏️
- 사용자가 올린 사진 및 Post를 저장한다.
- 포스트를 올린 사람의 id, 포스트의 내용, 사진 id 를 처리한다.
<br>

## kubernetes 빌드 및 배포 ✅
- jib 을 통해 AWS ECR 에 이미지를 배포합니다.

![스크린샷 2024-05-18 오후 3 59 57](https://github.com/sns-service/social-feed-server/assets/56336436/156e0020-caff-4c23-98cd-503811d1c04e)
<br>

- 루트 폴더의 deploy.yaml 을 실행하여 배포합니다.
> kubectl apply -f deploy.yaml
<br>

## 테스트 환경
https://www.telepresence.io/
<br>

- TelePresence로 로컬 환경에서 클러스터 내부의 서비스를 호출할 수 있게 해줍니다.
<br>

다운로드 후, 다음 명령어 실행
> telepresence helm install

> telepresence connect
