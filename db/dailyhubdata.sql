-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dailyhub
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `likes_tbl`
--

DROP TABLE IF EXISTS `likes_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `count` bigint DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq7bmv1ktdo5l696iswpnqkct8` (`post_id`),
  KEY `FKotn8qn1t2224cp8x5treps9jo` (`user_id`),
  CONSTRAINT `FKotn8qn1t2224cp8x5treps9jo` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`),
  CONSTRAINT `FKq7bmv1ktdo5l696iswpnqkct8` FOREIGN KEY (`post_id`) REFERENCES `post_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes_tbl`
--

LOCK TABLES `likes_tbl` WRITE;
/*!40000 ALTER TABLE `likes_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `likes_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `main_category_tbl`
--

DROP TABLE IF EXISTS `main_category_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `main_category_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `main_category_tbl`
--

LOCK TABLES `main_category_tbl` WRITE;
/*!40000 ALTER TABLE `main_category_tbl` DISABLE KEYS */;
INSERT INTO `main_category_tbl` VALUES (1,'fashion/beauty'),(2,'F&B'),(3,'health'),(4,'entertainment');
/*!40000 ALTER TABLE `main_category_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tbl`
--

DROP TABLE IF EXISTS `post_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `tag1` varchar(10) DEFAULT NULL,
  `tag2` varchar(10) DEFAULT NULL,
  `tag3` varchar(10) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `main_category_id` bigint DEFAULT NULL,
  `sub_category_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1eeci7llpdtcicipjsdd4xkhr` (`main_category_id`),
  KEY `FK2pfow9j4g1v2he2ypkt6r9h5` (`sub_category_id`),
  KEY `FKifvbkpi4pwmk3trv8tmjyjxo8` (`user_id`),
  CONSTRAINT `FK1eeci7llpdtcicipjsdd4xkhr` FOREIGN KEY (`main_category_id`) REFERENCES `main_category_tbl` (`id`),
  CONSTRAINT `FK2pfow9j4g1v2he2ypkt6r9h5` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category_tbl` (`id`),
  CONSTRAINT `FKifvbkpi4pwmk3trv8tmjyjxo8` FOREIGN KEY (`user_id`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tbl`
--

LOCK TABLES `post_tbl` WRITE;
/*!40000 ALTER TABLE `post_tbl` DISABLE KEYS */;
INSERT INTO `post_tbl` VALUES (1,'2025-01-06 17:54:36.000000','2025년에는 개인의 취미와 관심사를 깊이 있게 즐기는 초개인화 여행이 부각될 전망입니다. 1인 가구의 증가로 맛집 탐방이나 특정 취미를 위한 여행이 인기를 끌고, AI를 활용한 스마트 여행 계획 도구가 보편화되어 온라인 기반 여행 서비스의 성장이 기대됩니다.','여행','AI여행플랜',NULL,'나에게 맞는 초개인화 여행의 시대…2025년 여행자들을 위한 트렌드 보고서',NULL,4,6,1,'https://pimg.mk.co.kr/news/cms/202411/01/news-p.v1.20241030.71b08c9fe8244c88bd708423590a5a49_P3.jpg'),(2,'2025-01-06 17:54:37.000000','국제 여행 업계는 2025년 주요 트렌드로 환경 친화적인 ESG 여행, 스마트기기 사용을 줄이는 디지털 디톡스 여행, 그리고 기후변화로 인한 야간여행 수요 증가를 예상하고 있습니다. 지속 가능한 여행을 추구하는 여행객이 늘어나며, 밤 시간대에 즐길 수 있는 새로운 여행 콘텐츠가 주목받고 있습니다.','ESG여행','야간여행','디지털디톡스','2025년 여행 트렌드는 \'ESG\', \'디지털 디톡스\', \'야간여행',NULL,4,6,2,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQzJ9HNMX8iwfrwNhMEK9t7sS4HL1usN0vGQ&s'),(3,'2025-01-06 17:54:38.000000','호텔스닷컴은 2025년 주목할 여행 트렌드로 올 인클루시브 여행, 호텔 미식 여행, 굿즈 겟어웨이, SNS 추천 여행, 스크린 투어리즘 등을 선정했습니다. 여행객들은 여행지에서만 구할 수 있는 특별한 굿즈나 SNS에서 화제가 되는 장소를 찾아 떠나는 등 새로운 여행 형태에 관심을 보이고 있습니다.','굿즈여행','SNS추천여행','스크린투어리즘','‘굿즈’‘우회여행’…눈여겨보면 좋을 2025년 여행 트렌드는?',NULL,4,6,3,'https://img.khan.co.kr/news/2024/10/18/news-p.v1.20241018.154f4a66ba7c4571b072ff31d75f0e39_P1.webp'),(4,'2025-01-06 17:54:38.000000','국민의 70%가 휴가 기간에 여행을 선호하며, 스트레스 해소와 가족과의 추억 쌓기를 주요 목적으로 합니다. 개인의 취미와 관심사에 맞춘 개별 여행 스타일이 자리 잡고 있으며, 여행을 통해 일상에서 벗어나 리프레시하려는 경향이 두드러집니다.','가족여행','리프레시여행',NULL,'2024 마켓 트렌드 - 여행 편',NULL,4,6,4,'https://cdn.ibos.kr/design/upload_file/__HTMLEDITOR__/eduiboss/43SHLLCA57EP24RCOUE95FT27C2QSMWAEBAAF7VF_1729157483773625.png'),(5,'2025-01-06 17:54:39.000000','새해와 춘절을 맞아 중국인들의 국내외 여행 수요가 급증했습니다. 스키, 얼음, 온천 등의 계절성 키워드가 인기를 끌고 있으며, 프리미엄 여행과 해외여행 예약도 크게 늘었습니다. 다양한 문화 행사와 소비자 바우처 발행으로 국내 여행 활성화도 촉진되고 있습니다.','중국여행','새해여행',NULL,'[China 리포트] 새해 맞아 여행 간다…중국인 여행 수요 ‘쑥’',NULL,4,6,5,'https://cdn.traveltimes.co.kr/news/photo/202412/410537_36295_331.jpg'),(6,'2025-01-06 17:54:39.000000','오사카는 전통과 현대가 조화를 이루는 도시로, 가족과 함께하는 겨울 여행지로 추천됩니다. 오사카성 공원에서 역사와 자연을 느끼고, 유니버설 스튜디오 재팬에서 겨울 한정 이벤트와 화려한 퍼레이드를 즐기며 특별한 추억을 만들 수 있습니다.','일본여행','가족여행','겨울명소','겨울 오사카 해외여행 추천, 가족과 떠나는 감성 여행',NULL,4,6,6,'https://www.tournews21.com/news/photo/202501/82743_144894_135.jpg'),(8,'2025-01-06 17:54:40.000000','오키나와는 에메랄드빛 바다와 이국적인 풍경으로 유명하지만, 대중교통이 제한적이어서 렌트카 이용이 필수입니다. 초보자도 쉽게 따라 할 수 있도록 렌트카 대여 방법, 숙소 추천, 주요 명소 안내 등 단계별 여행 플랜을 제공하여 완벽한 여행 준비를 돕습니다.','일본여행','렌트카여행','초보여행꿀팁','[주말e PICK] 오키나와 렌트카·호텔 예약 꿀팁…일본 여행 완벽 준비',NULL,4,6,8,'https://cdn.foodneconomy.com/news/photo/202412/403000_202904_641.png'),(9,'2025-01-06 17:54:41.000000','하나투어는 겨울 시즌에만 즐길 수 있는 특별한 여행지로 삿포로와 하얼빈을 추천합니다. 삿포로 눈축제에서는 눈과 얼음으로 만든 다양한 조각상을 감상할 수 있고, 하얼빈 빙등제에서는 얼음 속에 조명을 밝혀낸 아름다운 야경을 즐길 수 있습니다. 온천과 운하 도시 오타루 등에서도 일본 특유의 감성을 느낄 수 있습니다.','겨울여행','일본축제','중국축제','하나투어 \"중국·일본 인기…겨울 맞이 시즌 여행 추천\\\"',NULL,4,6,9,'https://www.youthdaily.co.kr/data/photos/20241147/art_17320892356917_0b50c9.jpg'),(10,'2025-01-06 18:27:13.000000','알바몬 설문조사에 따르면, MZ세대 알바생들의 새해 소망 1위는 \'경제적 여유\'로 나타났습니다(75.3%). 연애·결혼(9.3%)이나 다이어트(8.5%) 등은 우선순위가 낮았으며, 새해 계획으로는 건강관리와 재테크가 주요 목표로 꼽혔습니다.','MZ세대','경제적자유','새해소망','MZ 알바생 새해 소망, ‘연애·결혼’ 보다 ‘경제적 자유’ 원해!',NULL,4,5,1,'https://cdn.casenews.co.kr/news/photo/202412/16955_37173_213.jpg'),(11,'2025-01-06 18:27:14.000000','연애를 포기하거나 비연애를 선택한 MZ세대의 현실을 다룬 기사입니다. 비용과 감정적 부담 때문에 홀로 살기를 선호하며, 연애 프로그램을 통해 간접 경험을 즐기는 사례도 많습니다.','비연애','MZ세대','연애관','MZ에게 직접 물었다 \"연애 안 하세요, 아님 못 하세요?\"',NULL,4,5,2,'https://ojsfile.ohmynews.com/STD_IMG_FILE/2024/0216/IE003261221_STD.jpg'),(12,'2025-01-06 18:27:14.000000','MZ세대는 끊임없는 경쟁과 바쁜 일상 속에서 연애를 포기하거나, 취향을 중시하며 가볍게 만나기를 선호합니다. 관련 시장은 이들의 새로운 연애 문화를 반영하며 변화하고 있습니다.','MZ연애트렌드','취향중시','연애문화','‘취향’은 필수, ‘능력’은 선택! MZ세대 연애 문화 트렌드',NULL,4,5,3,'https://openads-real.s3.amazonaws.com/openadsAdmin/images/contsImg/20240704161705773_YQ20wIdcgUZIYZPHcHrP.png'),(13,'2025-01-06 18:27:15.000000','아웃백과 방구석연구소가 협업하여 \'플러팅 테스트\'를 진행하며 MZ세대의 연애 관심사를 공략했습니다. 테스트는 플러팅 유형과 연애 성향을 분석하며 데이트 메뉴도 추천합니다.','플러팅테스트','MZ마케팅','아웃백','아웃백, ‘연애 플러팅 테스트’로 MZ세대 공략',NULL,4,5,4,'https://img.hankyung.com/photo/202501/01.39088450.1.jpg'),(14,'2025-01-06 18:27:15.000000','MZ세대 직장인 사이에서 효율성을 추구하는 \'다대다 소개팅\'이 유행하고 있습니다. 많은 사람을 한 번에 만날 수 있지만, 가벼운 만남이라는 부정적 평가도 공존합니다.','다대다소개팅','MZ직장인','효율만남','\"입사하자마자 매주 해요\"…요즘 MZ 직장인에 유행이라는데',NULL,4,5,5,'https://cdn.econovill.com/news/photo/202410/670179_622376_538.jpg'),(15,'2025-01-06 18:27:16.000000','MZ세대는 챗GPT와 같은 AI 기술을 활용해 연애 고민과 일상의 스트레스를 해결합니다. \'거울치료\'로 개인화된 상담 경험을 제공받으며 위로를 얻는 사례가 증가하고 있습니다.','챗GPT','연애상담','MZ라이프스타일','\"소개팅女 만나고 싶은데…\" MZ들 속 털어놓는 상담사 정체',NULL,4,5,6,'https://dimg.donga.com/wps/NEWS/IMAGE/2022/10/14/115946966.1.jpg'),(16,'2025-01-06 18:27:16.000000',' SNS 알고리즘을 통해 상대방의 관심사를 파악하고, \'알만추(알고리즘 만남 추구)\'가 연애 문화로 자리잡고 있습니다. 하지만 알고리즘 공유가 갈등을 초래하기도 합니다.','알고리즘연애','SNS파악','알만추','\"비키니女 사진 없겠지?\"…사귀기 전 \'알고리즘\' 뒤지는 MZ들',NULL,4,5,7,'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202406/04/05a1b3e9-b90f-4a2b-a29f-e89644ea57e9.jpg'),(17,'2025-01-06 18:28:20.000000','서울시가 주최한 한강 소개팅 행사에 3000명이 넘는 MZ세대가 신청하며 뜨거운 관심을 보였습니다. 신뢰도 높은 참여자 관리와 로맨틱한 한강 배경이 인기를 끌었습니다.','한강소개팅','서울시행사','MZ미팅','서울시가 주선하는 \'한강 소개팅\'…MZ남녀 3000여명 몰려 \'열기\'',NULL,4,5,8,'https://dimg.donga.com/ugc/CDB/WOMAN/Article/66/ff/b8/1c/66ffb81c0e73d2738250.jpg'),(18,'2025-01-06 18:35:16.000000','레이서들의 도전정신을 테마로 한 크림과 피치스의 2024 가을·겨울 \'모탈 레이서\' 컬렉션이 출시되었습니다. 강렬한 색감과 스포티한 디자인으로 역동적이며 현대적인 스타일을 선보입니다. 이번 컬렉션은 스피드와 열정을 패션으로 표현하며 젊은 층의 관심을 끌고 있습니다.','모탈레이서','크림피치스','스포티패션','크림, 피치스 24 가을·겨울 \'모탈 레이서\' 컬렉션 출시',NULL,1,1,1,'https://cdn.eyesmag.com/content/uploads/sliderImages/2024/12/11/01-284b3a1f-05fe-49b1-b86d-f00f5f8589c7.png'),(19,'2025-01-06 18:35:17.000000','2024년은 한국 패션 시장에 있어 ‘브랜드의 해’로, 로에베, 피비 파일로, 키스 등 글로벌 럭셔리 브랜드들이 국내 첫 플래그십 스토어를 오픈했습니다. K팝 스타와 협업하며 한국을 패션 중심지로 자리 잡게 한 이 브랜드들은 더 이상 직구가 아닌 현지 매장에서 직접 만나볼 수 있게 되었습니다.','럭셔리브랜드','한국패션시장','플래그십스토어','2024년 국내 공식 론칭한 브랜드 11',NULL,1,1,2,'https://cdn.eyesmag.com/content/uploads/posts/2024/12/27/펜디-06b0a2bd-e305-4a6d-843e-f3fc7cefbb8a.jpg'),(20,'2025-01-06 18:35:17.000000','코치와 마뗑킴의 협업 컬렉션이 젠지 세대의 감성을 담아 출시되었습니다. 클래식한 코치 스타일에 마뗑킴 특유의 감각적인 디자인을 더해 \'진정한 나를 표현한다\'는 메시지를 전달합니다. 이번 협업은 젊은 소비자들에게 독창적이고 세련된 아이템을 제안하며 큰 관심을 받고 있습니다.','코치마뗑킴','젠지패션','개성표현','코치 마뗑킴 컬렉션 출시',NULL,1,1,3,'https://cdn.eyesmag.com/content/uploads/sliderImages/2024/12/26/2412-COACHxMK-Digital-group-Horizontal-RGB-c5ce3601-178d-4be2-920d-f9ee9727fd7d.jpg'),(21,'2025-01-06 18:35:18.000000','디젤이 2025년 뱀의 해를 기념해 트롱프뢰유와 패치워크 기법을 활용한 캡슐 컬렉션을 선보였습니다. 뱀의 신비롭고 강렬한 상징을 패션 아이템으로 재해석한 이번 컬렉션은 젊음과 변화를 상징하며, 브랜드 특유의 대담한 미학을 담고 있습니다.','디젤','캡슐컬렉션','뱀의해패션','디젤, 뱀의 해 기념 캡슐 컬렉션 공개',NULL,1,1,4,'https://cdn.eyesmag.com/content/uploads/sliderImages/2025/01/03/2-f641fb00-020d-4a9a-b35a-229f9419bee0.jpg'),(22,'2025-01-06 18:35:18.000000','아웃도어 브랜드 더왈드가 첫 컬렉션 \'Hiking with your Company\'를 출시했습니다. 자연 속 여유와 동행의 의미를 담은 아이템들로, 실용성과 감각적인 디자인을 조화롭게 선보였습니다. 특히 하이킹과 모험에서 영감을 받은 디테일이 돋보입니다.','더왈드','첫컬렉션','아웃도어패션','더왈드, 첫 번째 컬렉션 공개',NULL,1,1,5,'https://cdn.eyesmag.com/content/uploads/sliderImages/2025/01/03/13-c5497f05-ef02-4875-9438-aa6ec85d9e7b.jpg'),(23,'2025-01-06 18:35:19.000000','닥터마틴이 기존의 노란 스티치를 제거한 1461 신작을 출시하며 새로운 변화를 시도했습니다. 미니멀하면서도 현대적인 디자인으로 재탄생한 이번 아이템은 클래식과 세련미를 동시에 추구하는 소비자들에게 어필하고 있습니다.','닥터마틴','1461신작','심플디자인','노란 스티치가 사라진, 닥터마틴 1461 신작 공개',NULL,1,1,6,'https://cdn.eyesmag.com/content/uploads/sliderImages/2025/01/03/7-7499318f-9e1a-4808-abdc-c2c1a65cc02d.jpg'),(24,'2025-01-06 18:35:19.000000','크림이 2024년 소비자 인사이트 리포트를 발표했습니다. 리포트에 따르면 월요일 밤 10시가 패션 쇼핑의 주요 시간대로 나타났으며, 소비자들이 구매 결정을 내리는 심리적 트렌드와 패턴이 분석되었습니다. 이는 패션 마케팅의 중요한 참고 자료로 주목받고 있습니다.','크림인사이트','패션트렌드','소비자분석','크림, 2024 인사이트 리포트 발표',NULL,1,1,7,'https://cdn.eyesmag.com/content/uploads/sliderImages/2024/12/31/WEB_%EA%B8%B0%EC%82%AC%EC%83%81%EB%8B%A8%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C_01-93f167ca-e671-4b09-9e15-4f438ef33807.png'),(25,'2025-01-06 18:35:20.000000','뱀의 해를 맞아 다양한 패션 아이템들이 뱀의 매력을 담아 출시되었습니다. 스네이크 패턴과 뱀 형상을 활용한 가방, 옷, 오브제 등은 변화와 성장을 상징하며 강렬하고 세련된 이미지를 전달합니다. 새해를 맞아 새로운 스타일을 시도하려는 이들에게 독특한 선택지를 제공합니다.','스네이크패턴','뱀의해아이템','트렌디패션','세련된 본능미, 사랑스러운 뱀의 매력을 담은 아이템 9',NULL,1,1,8,'https://cdn.eyesmag.com/content/uploads/sliderImages/2024/12/26/1-20750d01-bfc0-490c-9e3c-e9157f3e229f.jpg'),(26,'2025-01-06 18:40:19.000000','광주 지역 직장인들의 점심 고민을 덜어줄 맛집 리스트가 공개되었습니다. ‘무등숯불갈비’, ‘마싯계 닭갈비’, ‘오감탄’은 각각 보리밥 정식, 치즈 닭갈비, 불고기 한상으로 유명합니다. 맛과 양, 분위기를 모두 갖춘 이 맛집들은 점심뿐 아니라 저녁에도 적합한 곳으로 가족 단위 방문객들에게도 추천됩니다.','광주맛집','직장인점심','무슐랭맛집','[무슐랭] 점심 뭐 먹지..? 광주 직장인 점심 맛집 추천!',NULL,2,4,1,'http://www.mdilbo.com/lib/thumb.html?type=file&src=202412/09/20241209103746422095.jpg&w=700'),(27,'2025-01-06 18:40:20.000000','버스 타고 핫플-맛집 탐방...중국 MZ세대 제주 생활권으로','제주핫플','제주감귤',NULL,'버스 타고 핫플-맛집 탐방...중국 MZ세대 제주 생활권으로',NULL,2,4,2,'https://cdn.jejusori.net/news/photo/202408/429553_450675_1429.jpg'),(28,'2025-01-06 18:40:21.000000','네이버 ‘히든아카이브’, 카카오 ‘요즘뜨는’, 티맵 ‘어디갈까’ 등 지도 데이터 기반의 장소 추천 서비스가 인기를 끌고 있습니다. 실제 주행 기록, 저장 데이터 등 신뢰성 높은 정보를 바탕으로 핫플레이스와 맛집을 추천하며, 기존 서비스의 신뢰성 문제를 해결하는 데 주력하고 있습니다.','핫플추천','지도서비스','데이터기반장소추천','요즘 뜨는 장소 알려줘...지도 데이터 기반 핫플 큐레이션 경쟁',NULL,2,4,3,'https://cdn.digitaltoday.co.kr/news/photo/202411/543180_507542_3346.png'),(29,'2025-01-06 18:40:21.000000','군위군의 야경 명소와 현지인만 아는 특별한 핫플레이스를 소개합니다. 삼국유사의 흔적이 남아 있는 화산산성 전망대와 색색의 유리구슬로 꾸며진 항아리 예술 작품은 낮과 밤의 아름다움을 동시에 선사합니다. 이 방송은 현지의 매력을 재발견하고 지역 관광을 활성화하는 데 초점을 맞추고 있습니다.','핫플추천','야경맛집','현지인추천','[한국기행] 우리 동네 핫플레이스 4부 - 군위의 야경 맛집',NULL,2,4,4,'https://img.siksinhot.com/place/1450234212425672.jpg?w=728&h=400&c=Y'),(30,'2025-01-06 18:40:22.000000','대기업들이 운영하는 인스타그램 계정이 Z세대 소비자들과의 소통 창구로 주목받고 있습니다. 현대자동차의 ‘르르르’ 계정은 민달팽이 캐릭터를 통해 대중적인 콘텐츠를 제작하며 친근감을 더합니다. SNS 계정을 통해 기업 이미지를 부드럽게 만들고 소비자와 심리적 거리감을 좁히는 데 성공하고 있습니다.','SNS마케팅','인스타맛집',NULL,'\"대기업은 콘텐츠도 잘 만드네..\" 인스타 맛집 기업 계정 모음.zip',NULL,2,4,5,'https://newneek.co/_next/image?url=https%3A%2F%2Fd2phebdq64jyfk.cloudfront.net%2Fmedia%2Farticle%2F9bc40df9bfa34a5189627607c69eb309.png&w=1920&q=75'),(31,'2025-01-06 18:40:22.000000',' SNS에 소개된 유명 맛집이 대가성 광고에 의해 과장된 사례로 밝혀지며 소비자들에게 실망감을 안겼습니다. 블로거와 인플루언서를 활용한 ‘뒷광고’의 확산은 소비자 신뢰를 떨어뜨리고 있으며, 이를 방지하기 위한 투명성과 신뢰성 강화의 필요성이 제기되고 있습니다.','뒷광고논란','SNS맛집','소비자보호','\"30분 줄 서서 먹었는데\"…유명 맛집 알고 보니 \'충격\'',NULL,2,4,6,'https://img.hankyung.com/photo/202409/99.35215101.1.jpg'),(32,'2025-01-06 18:40:23.000000',' BTS 정국과 베컴이 방문해 화제를 모은 ‘금돼지 식당’은 2024 미슐랭 가이드에 선정된 서울의 인기 삼겹살 맛집입니다. 대표 메뉴인 본삼겹은 쫀득한 식감이 특징이며, 바질 쌈과 김치찌개는 고기의 풍미를 더해줍니다. 품질 높은 음식과 특별한 조합으로 많은 사람들의 사랑을 받고 있습니다.','삼결살맛집','서울맛집','금돼지식당','연예인도 찾는 서울 삼겹살 맛집',NULL,2,4,7,'https://img.allurekorea.com/allure/2024/02/style_65dfd56945670-960x1200.jpg'),(33,'2025-01-06 18:47:37.000000','걷기는 심혈관 건강 개선, 지방 연소, 근육 강화 등 다양한 이점을 제공합니다. 러킹(배낭을 메고 걷기)은 칼로리 소모를 높이고, 계단 걷기는 근력 강화에 효과적입니다. 올바른 자세와 호흡법으로 운동 효과를 극대화할 수 있으며, 하루 6천 보 걷기는 장기적인 건강 유지에 도움을 줍니다. 전문가들은 규칙적인 걷기가 사망률 감소와 뇌 기능 활성화에 긍정적인 영향을 미친다고 강조합니다.','걷기운동','러킹효과','건강관리','“걷기, 그게 운동이 되겠어?”라는 질문에 대한 과학의 답변',NULL,3,8,1,'https://dimg.donga.com/wps/NEWS/IMAGE/2025/01/02/130775285.3.jpg'),(34,'2025-01-06 18:47:38.000000','제시카 알바는 운동복 착용으로 건강미를 선보였습니다. 레깅스는 혈액순환과 근육 보호에 도움을 주고, 스포츠 브라는 유산소 운동 시 가슴 움직임을 줄이며 운동 효율성을 높입니다. 적절한 운동복 선택은 운동 중 부상을 예방하고 근육 활동을 정확히 파악하게 해줍니다. 알바의 사례는 운동복이 단순한 의류를 넘어 실용적 가치를 지닌 아이템임을 보여줍니다.','제시카알바','운동복효과','운동스타일','“애 셋 엄마 맞아?” 제시카 알바, 운동복으로 뽐낸 건강미',NULL,3,8,2,'https://health.chosun.com/site/data/img_dir/2025/01/03/2025010301913_0.jpg'),(35,'2025-01-06 18:47:39.000000','배우 정영숙은 하루 6천 보 걷기를 통해 건강을 유지합니다. 빠르게 걷기와 천천히 걷기를 번갈아 시행하며 지방 연소와 근육 강화를 도모합니다. 걷기는 혈액순환 촉진, 뇌 기능 활성화, 사망률 감소 등에 긍정적인 영향을 미치며, 올바른 자세와 꾸준한 실천이 운동 효과를 극대화합니다.','정영숙건강','걷기비법','운동효과','‘국민 배우’ 77세 정영숙, 걷기로 관리하는 건강 비법',NULL,3,8,3,'https://health.chosun.com/site/data/img_dir/2025/01/03/2025010301574_0.jpg'),(36,'2025-01-06 18:47:39.000000','[무슐랭] 점심 뭐 먹지..? 광주 직장인 점심 맛집 추천!','만성질환예방','운동과건강','식습관개선','당뇨병, 천식 등 만성병 예방과 관리를 위한 운동 방법',NULL,3,8,4,'https://cdn.hankyung.com/photo/201407/AA.8908285.1.jpg'),(37,'2025-01-06 18:47:40.000000','일부 흡연자들이 건강한 사례를 예로 들어 흡연의 위험성을 축소하려 하지만, 이는 통계적으로 극히 드문 경우입니다. 흡연은 심혈관계 질환과 암 발병 위험을 크게 증가시키며, 금연은 건강 유지와 기대 수명 증가의 필수 요소로 강조됩니다.','흡연의위험','금연필요성','건강수명','',NULL,3,8,5,'https://cdn.casenews.co.kr/news/photo/201908/2697_10600_2747.jpg'),(38,'2025-01-06 18:47:40.000000','댈러스 매버릭스 구단주 마크 큐반은 산책과 채식, 과학적 노화 방지 방법을 실천하며 건강을 유지합니다. 그는 생물학적 나이를 20대처럼 유지하며 활동적이고 활기찬 삶을 살고 있습니다. 큐반의 사례는 장수를 위한 라이프스타일의 중요성을 보여줍니다.','마그큐반','건강비결','장수라이프스타일','‘억만장자’ NBA 구단주의 건강 비결',NULL,3,8,6,'https://dimg.donga.com/wps/NEWS/IMAGE/2025/01/05/130788212.1.jpg'),(39,'2025-01-06 18:47:41.000000','겨울철 활동량 감소와 혈액순환 저하로 좌골신경통 증상이 악화될 수 있습니다. 적절한 스트레칭과 올바른 자세를 유지하면 증상 완화에 도움이 되며, 지속적인 관리로 신경 손상 및 근력 약화를 예방할 수 있습니다. 따뜻한 환경에서 활동량을 늘리는 것도 중요합니다.','좌골신경통관리','요통예방','겨울운동','추운 날씨에 더 심해지는 좌골신경통 예방과 관리법',NULL,3,8,7,'https://image.zdnet.co.kr/2025/01/04/775908a6d9c7592a0961556f5ae4039c.jpg'),(40,'2025-01-06 18:47:41.000000','채식과 꾸준한 운동을 병행한 한 의사는 체중 감량과 혈압, 혈당 개선에 성공했습니다. 채식은 지방간 완화와 혈액 수치 정상화에도 효과적이며, 건강과 환경 지속 가능성 모두에 긍정적인 영향을 미칩니다.','채식효과','건강개선','환경지속가능성','‘붉은 고기’를 줄이고 채식을 선택한 삶의 변화',NULL,3,8,8,'https://flexible.img.hani.co.kr/flexible/normal/727/485/imgdb/original/2025/0101/20250101502594.jpg'),(41,'2025-01-06 18:47:42.000000','송편버섯이 근육세포를 보호하고 증식을 촉진한다는 연구 결과가 발표되었습니다. 국립생물자원관과 선문대 김승영 교수 연구진은 송편버섯 배양액이 근감소증 예방에 효과적임을 확인했습니다. 송편버섯은 국내외 여러 지역에 분포하며 인공 배양이 가능하고, 주로 죽은 활엽수에서 자라는 목질 버섯으로 알려져 있습니다. 이는 근손실에 민감한 헬스인들과 중노년층에게 유용한 정보로 평가받고 있습니다.','송편버섯','근감소증예방','헬스정보','송편 버섯, 근 손실 막아준다고? \'득근\' 원하는 헬창들 주목',NULL,3,8,9,'https://cdn.dailycc.net/news/photo/202406/790132_697822_388.jpg'),(42,'2025-01-06 18:47:42.000000','격투기 스타 밥 샙은 두 명의 아내를 공개하며 일부다처제 생활을 소개했습니다. 두 아내는 밥 샙의 운동과 일상 생활을 적극 내조하며 화목한 관계를 유지하고 있습니다. 밥 샙은 운동과 함께 유산소 세션을 숙소에서 아내들과 함께하며 스태미나를 유지하는 것이 원동력이라고 밝혔습니다. 그의 삶은 유쾌한 일화들과 함께 헬스인의 관심을 끌고 있습니다.','밥샙','일부다처제','헬스스타','밥 샙, 두 아내 최초 공개…\"침대서 하는 유산소 운동, 스태미나 2배 필요',NULL,3,8,10,'https://www.news1.kr/_next/image?url=https%3A%2F%2Fi3n.news1.kr%2Fsystem%2Fphotos%2F2024%2F5%2F3%2F6631774%2Fhigh.jpg&w=1920&q=75'),(43,'2025-01-06 18:54:44.000000','네바다주 라스베이거스에서 테슬라 사이버트럭 폭발로 1명이 사망하고 7명이 다쳤습니다. 경찰은 뉴올리언스에서 발생한 차량 돌진 테러와의 연관성을 조사 중입니다. 일론 머스크는 차량 결함이 아닌 렌트 차량 내 폭탄이 원인이라고 밝혔으며, FBI가 테러 가능성을 포함해 사건을 철저히 조사하고 있습니다.','테슬라사이버트럭','테러조사','FBI수사','테슬라 사이버트럭 폭발, 테러와 연관 있다?',NULL,4,7,1,'https://cdn.eyesmag.com/content/uploads/posts/2025/01/02/tesla-cybertruck-explosion-outside-tesla-ffecf629-00f9-4f0d-a618-6df6bd12cae8.jpg'),(44,'2025-01-06 18:54:44.000000','2025년부터 대부분의 전자기기는 USB-C 충전단자로 통일됩니다. 이 제도는 전자폐기물을 줄이고 소비자 편의를 높이기 위한 조치로, 스마트폰, 태블릿, 노트북 등 총 13종의 기기에 적용됩니다. 노트북은 2026년 4월부터 시행됩니다.','USB_C통일','전자폐기물감소','소비자편의','새해부터 전자기기 충전단자 C타입으로 통일된다',NULL,4,7,2,'https://cdn.eyesmag.com/content/uploads/posts/2024/12/31/03-5f651f8d-5bac-4de1-84f9-e8adbc9ee957.jpg'),(45,'2025-01-06 18:54:45.000000','깊고 편안한 수면을 돕는 아이템들이 소개되었습니다. 수면은 새로운 하루를 준비하는 중요한 활동으로, 다양한 수면 보조 제품과 기술을 활용해 보다 나은 수면 환경을 조성할 수 있습니다.','수면아이템','꿈잠비법','수면환경','올해는 푹 자고 싶은 당신을 위한 수면 아이템',NULL,4,7,3,'https://cdn.eyesmag.com/content/uploads/posts/2024/12/31/2025-sleep-item-recomend-6-3740b2f2-2380-49ef-ab38-9b7dd6687349.jpg'),(46,'2025-01-06 18:54:45.000000','홍콩 M+ 미술관에서 열리는 피카소와 아시아 디아스포라 작가들의 작품 전시가 주목받고 있습니다. 이 전시는 세대 간 대화를 조명하며, 설치 및 퍼포먼스를 포함한 대규모 작품들을 선보이는 가장 야심 찬 전시입니다.','피카소전시','홍콩M플러스','아시아예술','올해 가봐야 할 해외 전시 7',NULL,4,7,4,'https://cdn.eyesmag.com/content/uploads/posts/2025/01/03/0-ce308ff8-2597-4ed8-88f7-76022a23e3ef.jpg'),(47,'2025-01-06 18:54:46.000000','공수처가 윤석열 대통령의 체포 및 수색 영장을 집행하려 했으나 경호구역 내 수색 불허로 인해 중지되었습니다. 공조수사본부는 집행 저지를 이유로 5시간 만에 철수하며 유감을 표명했습니다.','윤석열체포영장','공수처수사','대통령경호법','윤석열 대통령의 체포영장 집행이 중지됐다',NULL,4,7,5,'https://cdn.eyesmag.com/content/uploads/posts/2025/01/03/1-e484a4e3-4a20-4485-a821-e1672e7cbc5f.jpg'),(48,'2025-01-06 18:54:46.000000','오래 앉아 있는 생활 습관은 자궁근종, 유방암 등 여성 질환의 위험을 증가시킵니다. 중국 쿤밍 의대 연구에 따르면 좌식 생활이 대사 장애와 만성 염증을 유발하며, 이를 예방하기 위해 규칙적인 운동과 자세 교정이 필요합니다.','좌식생활','여성건강','자궁근종예방','오래 앉아 일하는 여성이 주의해야 할 질환',NULL,4,7,6,'https://cdn.eyesmag.com/content/uploads/posts/2025/01/03/1-34c29a76-ae43-4e68-b3b1-523d2e00b7e6.jpg'),(49,'2025-01-06 18:54:47.000000','뉴욕 모마(MoMA)에서 샤넬 후원으로 복원된 희귀 영상 작품을 상영하는 <To Save And Project> 영화제가 열립니다. 독일 표현주의 작품부터 복원된 소련 걸작까지 다양한 국가의 영화들이 상영되며, 글로벌 영화 보존의 중요성을 조명합니다.','모마영화제','샤넬후원','영화보존','모마, 샤넬이 후원한 영화제를 개최한다',NULL,4,7,7,'https://cdn.eyesmag.com/content/uploads/sliderImages/2025/01/03/111-58e367ac-d35a-4bba-8c22-4e9b06871032.jpg'),(50,'2025-01-06 18:54:48.000000','1995년 방영된 드라마 <모래시계>가 넷플릭스에서 리마스터링되어 공개되었습니다. 민주화 운동을 배경으로 한 이 작품은 최민수, 고현정, 박상원의 열연으로 큰 인기를 끌며, 당시 최고 시청률 64.5%를 기록한 한국 드라마의 명작입니다.','넷플릭스신작','모래시계','한국드라마','2025년 1월 첫 주, 넷플릭스 신작 라인업',NULL,4,7,8,'https://cdn.eyesmag.com/content/uploads/posts/2025/01/02/57-555c8815-a67e-48bb-a80f-8c8242bcc92b.jpg'),(51,'2025-01-06 19:00:29.000000','서울 동대문 DDP에서 열린 \'K뷰티 부스트\'에는 16개 K뷰티 브랜드와 1000여 명의 글로벌 인플루언서가 참여했습니다. 이들은 한국 화장품의 개인 맞춤형 솔루션과 흡수력, 탄력 개선 등의 강점을 경험하며 높은 만족감을 표했습니다. 특히, 인플루언서들의 경험담과 브랜드 부스 이벤트가 호응을 얻었습니다.','K뷰티','글로벌인플루언서','뷰티페스티벌','[르포] 글로벌 뷰티 인플루언서 1000명, K뷰티에 \'엄지척\'',NULL,1,2,1,'https://menu.moneys.co.kr/moneyweek/thumb/2024/12/30/06/2024123014562854630_1.jpg/dims/optimize/'),(52,'2025-01-06 19:00:30.000000','뷰티 인플루언서 데이즈는 메이크업 튜토리얼, 화장품 리뷰, 오프라인 행사 체험 콘텐츠를 통해 영향력을 넓히고 있습니다. 최근 인기 콘텐츠로는 프라다 뷰티 립스틱 리뷰와 샤넬 팝업스토어 체험기가 있습니다. 데이즈는 뷰티와 윤리적 소비를 결합한 콘텐츠로 성장하고 있습니다.','뷰티인플루언서','메이크업리뷰','뷰티트렌드','뷰티 인플루언서 ‘데이즈’, 한발 앞선 트렌드로 영향력 확대',NULL,1,2,2,'http://tnnews.co.kr/wp-content/uploads/2024/12/프로필0.png'),(53,'2025-01-06 19:00:30.000000','서울콘 \'K뷰티 부스트\'는 글로벌 뷰티 인플루언서 1000여 명이 참석한 대규모 행사로, K뷰티의 글로벌 위상을 알리는 기회가 되었습니다. 브랜드 발표와 K뷰티 컨퍼런스는 참여자들에게 제품의 혁신성과 시장 확대 가능성을 강조했습니다.','K뷰티','서울콘','뷰티컨퍼런스','[현장] 글로벌 인플루언서 1000명, 서울서 한자리 \'K뷰티에 빠졌다\'',NULL,1,2,3,'https://cdn.newsian.co.kr/news/photo/202412/75810_60558_4320.jpg'),(54,'2025-01-06 19:00:31.000000',' K뷰티 기업들은 인플루언서와의 협업을 통해 맞춤형 화장품 시장을 공략하고 있습니다. 에뛰드와 효진조의 쉐이딩 제품, 레오제이와 투쿨포스쿨의 픽싱 쿠션 등이 큰 성공을 거두었으며, 이러한 협업은 바이럴 효과와 지속적인 브랜드 인지도를 유지하는 데 기여하고 있습니다.','K뷰티협업','맞춤형화장품','뷰티트렌드','뷰티업계-인플루언서 \'제품 협업\' 활발...왜?',NULL,1,2,4,'https://cdn.digitaltoday.co.kr/news/photo/202408/530231_494939_252.jpg'),(55,'2025-01-06 19:00:31.000000','노석지 대표가 개발한 \'닥터로사\'와 \'잇라피\'는 글로벌 인플루언서들에게 큰 호응을 얻었습니다. 닥터로사는 바이오 기술을 활용한 메디컬 뷰티 제품이며, 잇라피는 1020 세대를 겨냥한 감성적 제품입니다. 두 브랜드는 K뷰티의 글로벌 성공 가능성을 입증했습니다.','닥터로사','잇라피','메디컬뷰티','인핸스비 노석지 대표 야심작 화장품, \"글로벌 인플루언서들 반했다\"',NULL,1,2,5,'https://www.meconomynews.com/news/photo/202412/106736_126454_1945.jpg'),(56,'2025-01-06 19:00:32.000000','K뷰티 브랜드들이 AI를 활용해 뷰티 인플루언서를 매칭하며 글로벌 마케팅에 박차를 가하고 있습니다. \'스킨1004\'는 AI 솔루션을 통해 인플루언서 후보 선정, 계약, 콘텐츠 분석 등을 자동화하며 해외 매출의 95%를 차지하는 성과를 냈습니다. 코스알엑스와 티르티르 등도 각국 인플루언서와 협업해 틱톡 챌린지나 맞춤형 제품 영상으로 높은 조회수와 매출 상승을 기록했습니다.','K뷰티마케팅','AI솔루션','인플루언서협업','AI로 K뷰티 \'찰떡\' 인플루언서 매칭',NULL,1,2,6,'https://img.hankyung.com/photo/202409/AA.37992257.1.jpg'),(57,'2025-01-06 19:00:32.000000','K뷰티의 글로벌 성공 뒤에는 각국 인플루언서와의 협업이 있습니다. 대형 스타 대신 현지 맞춤형 인플루언서를 통해 브랜드를 알리는 전략이 효과를 발휘하며, 로컬 마케팅과 SNS 캠페인으로 소비자 신뢰를 얻고 있습니다. 이는 국내 중소 브랜드의 해외 시장 진출과 브랜드 인지도 상승에 큰 기여를 하고 있습니다.','K뷰티','글로벌브랜드','인플루언서전략','K-뷰티 성공 주역은 \'인플루언서\'… 국내 중소 브랜드 글로벌 인지도 높인다',NULL,1,2,7,'https://img.newspim.com/news/2024/09/11/2409111619208790.jpg'),(61,'2025-01-07 21:35:51.223668','1 고추장(1T), 고춧가루(1T), 설탕(1T), 올리고당(1T) 다진마늘(1/2T), 간장(1T), 식초(1.5T), 참기름(1T), 통깨(1T) 섞어 양념장 만들어요\n2 물기 꼭 짜 주고\n3 슬라이스한 양파도 넣어요\n4 준비한 양념장 넣고 무쳐 냅니다\n5 새콤달콤 맛있는 오이무침 완성입니다 수분을 꼭 짜고 무친 것이라 꼬들꼬들 아삭함이 좋은 오이무침 입니다 맛있어요 ㅎ','오이','백종원','','백종원 오이무침 새콤달콤 맛있게~','2025-01-07 21:35:51.223668',2,3,1,'https://recipe1.ezmember.co.kr/cache/recipe/2018/10/06/b1b388113cd0d3c3c3ce3838f838bfef1.jpg'),(62,'2025-01-07 21:37:27.139018','백종원님의 레시피중에 간혹 감탄이 나오는\n레시피가 있는데 바로 오징어볶음입니다.\n살짝 불향까지 나는것이 아주 일품이네요.\n쉬운 레시피로 따라하기도 쉽습니다.\n오늘 메뉴로 해 보세요~','오징어','볶음','','오징어 볶음, 향과 맛이 일품! 백종원 오징어 볶음','2025-01-07 21:37:27.139018',2,3,1,'https://recipe1.ezmember.co.kr/cache/recipe/2019/01/04/518d5bf35102aa51bf58078f7a25dc751.jpg'),(63,'2025-01-07 21:39:48.113213','냉동고에 보관한 밤 크림과 크림빵의 크림을 섞은 다음 토피넛 라떼에 적신 다이제 위에 올렸습니다. 그리고 요거트 그래놀라에서 그래놀라만 꺼내 살짝 볶은 후, 완성된 티라미수 위에 얹어 크런치한 식감을 더했죠.','나폴리맛피아','밤티라미수','흑백요리사','안성재도 리필한 나폴리 맛피아의 \'밤 티라미수\' 만드는 법','2025-01-07 21:39:48.113213',2,3,1,'https://thumb.mt.co.kr/06/2024/10/2024101615441045934_1.jpg/dims/optimize/');
/*!40000 ALTER TABLE `post_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category_tbl`
--

DROP TABLE IF EXISTS `sub_category_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_category_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sub_category_name` varchar(50) NOT NULL,
  `main_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhto01q1mh2j25pstw3n30l9fy` (`main_category_id`),
  CONSTRAINT `FKhto01q1mh2j25pstw3n30l9fy` FOREIGN KEY (`main_category_id`) REFERENCES `main_category_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category_tbl`
--

LOCK TABLES `sub_category_tbl` WRITE;
/*!40000 ALTER TABLE `sub_category_tbl` DISABLE KEYS */;
INSERT INTO `sub_category_tbl` VALUES (1,'fashion',1),(2,'beauty',1),(3,'recipe',2),(4,'hotplace',2),(5,'love',4),(6,'travel',4),(7,'etc',4),(8,'health',3);
/*!40000 ALTER TABLE `sub_category_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday` date DEFAULT NULL,
  `nickname` varchar(10) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `join_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` VALUES (1,'1998-04-07','JHM','$2a$10$ouaCQ0XlWIpDD./ATRcPV..NIDeIQEeLt0J1FFnzWhLZkMP5fKwhO','01011112222','USER','wlsgusan','2025-01-04'),(2,'2000-01-01','gusan1','$2a$10$xkTMoQSAbDpGfwBhSuCV3Ob0nh2cDCjgKBgos2mgmC8EanlyAL/Tq','01011112222','USER','wlsgusan1','2025-01-05'),(3,'2000-01-02','gusan2','$2a$10$aTZ2R7uiyLYJU12ruJ/Y6.rBRGY.A6nRm67RqYQY84iHcM.6.0Oje','01011111112','USER','wlsgusan2','2025-01-05'),(4,'2000-01-03','gusan3','$2a$10$.hvXQsNvqrt3exq5eoRtcugxYr2DDTBQewSsGoV6qrgQUv8mR3m.m','01011111113','USER','wlsgusan3','2025-01-05'),(5,'2000-01-04','gusan4','$2a$10$ABjy/8lv4EL1g1fX4jt5S.OVF6rqM/zGJwKskxXKU6hOO46ky5KhO','01011111114','USER','wlsgusan4','2025-01-05'),(6,'2000-01-05','gusan5','$2a$10$HKIJcAPp2.lhIknx4cFUeOrj0cj0Ow7iHIUTzYujR4rkYTvn5W4LO','01011111115','USER','wlsgusan5','2025-01-05'),(7,'2000-01-06','gusan6','$2a$10$22x5SsBHegSDXuMWpH5s2.STlfXtpUL2tCZ09dv5dwnujV.c66rQy','01011111116','USER','wlsgusan6','2025-01-05'),(8,'2000-01-07','gusan7','$2a$10$x3cSUfZGWfhq/6FX22NPxuH/c2w./XKZoim22nKqVDKyXGsP7Kmxa','01011111117','USER','wlsgusan7','2025-01-05'),(9,'2000-01-08','gusan8','$2a$10$07yDMcqsyS/xHGe7tw2pfuAioTtxj8gXAa6xGNagR0YAJhLdiKUS.','01011111118','USER','wlsgusan8','2025-01-05'),(10,'2000-01-09','gusan9','$2a$10$yp2Z9KustuzU0JPGgINu1O4uAruXLcXhxbezJ5fqPI1eWkeasJPrO','01011111119','USER','wlsgusan9','2025-01-05'),(11,'2000-01-10','gusan10','$2a$10$ZoQTfNo3IGe8DknLJAi/e.BlK6kxZojvdgbR23Nv9AeWVVyB6GXze','01011111120','USER','wlsgusan10','2025-01-05');
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-08  0:34:17
