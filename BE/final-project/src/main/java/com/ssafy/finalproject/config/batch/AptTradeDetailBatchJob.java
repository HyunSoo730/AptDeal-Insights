package com.ssafy.finalproject.config.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import com.ssafy.finalproject.data.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AptTradeDetailBatchJob {

    // 지도 뿌리기 테스트용 MemoryRepository임 ㅇㅇ. 프로젝트 진행과 무관
//    private final MemoryAptSaleRepository memoryAptSaleRepository;

    private final AptSaleRepository aptSaleRepository;

    private final WebClient webClient;
    private final PlatformTransactionManager transactionManager;
    private static final String SEOUL_BASE_URL = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
    private static final String ENCODED_API_KEY = "iKsPFx8Qh%2F4qz8Ys3N2Q5pNtBD5Qcjg6UdYhj1zQ%2BVMy49hTMhJ65auzfFDdpx11fX4ABr0glasN9QP%2B3QmKeQ%3D%3D";
    private static final String SEOUL_API_KEY = URLDecoder.decode(ENCODED_API_KEY, StandardCharsets.UTF_8);

    @Bean
    public Job aptTradeDetailJob(JobRepository jobRepository) {
        log.info("********** 아파트 매매 상세자료 조회 배치 작업 실행 ***********");
        return new JobBuilder("aptTradeDetailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(aptTradeDetailStep(jobRepository))
                .next(aptCoordinateStep(jobRepository))
                .build();

    }

    @Bean
    public Step aptTradeDetailStep(JobRepository jobRepository) {
        log.info("aptTradeDetailStep 호출");
        return new StepBuilder("aptTradeDetailStep", jobRepository)
                .<AptSaleDTO, AptSaleDTO>chunk(100, transactionManager)
                .reader(aptTradeDetailReader())
                .writer(aptTradeDetailWriter())
                .allowStartIfComplete(true)
                .build();
    }


    // ! XML로 파싱해서 읽어올 부분, WebClient는 OPEN API 호출할 때마다 URL 수정해야함 (mutate)
    @Bean
    public Step aptCoordinateStep(JobRepository jobRepository) {
        return new StepBuilder("aptCoordinateStep", jobRepository)
                .<AptSale, AptSale>chunk(100, transactionManager)
                .reader(aptCoordinateReader())
//                .processor(aptCoordinateProcessor())
                .writer(aptCoordinateWriter())
                .allowStartIfComplete(true)
                .build();
    }

    private Stream<String> generateYMDParameters() {
        return IntStream.rangeClosed(1, 17)
                .mapToObj(month -> {
                    int year = (month <= 12) ? 2023 : 2024;
                    int adjustedMonth = month % 12;
                    if (adjustedMonth == 0) {
                        adjustedMonth = 12;
                    }
                    return String.format("%d%02d", year, adjustedMonth);
                });
    }

    @Bean
    public ItemReader<AptSaleDTO> aptTradeDetailReader() {

        String[] ymdParams = IntStream.rangeClosed(1, 17)
                .mapToObj(month -> {
                    int year = (month <= 12) ? 2023 : 2024;
                    int adjustedMonth = month % 12;
                    if (adjustedMonth == 0) {
                        adjustedMonth = 12;
                    }
                    return String.format("%d%02d", year, adjustedMonth);
                })
                .toArray(String[]::new);

        String[] lawdCodes = {"11680", "11740", "11305", "11500", "11620", "11215", "11530", "11545", "11350", "11320",
                "11230", "11590", "11440", "11410", "11650", "11200", "11290", "11710", "11470", "11560",
                "11170", "11380", "11110", "11140", "11260"};


        return new ItemReader<AptSaleDTO>() {
            private int lawdIndex = 0;
            private int ymdIndex = 0;

            @Override
            public AptSaleDTO read() throws JsonProcessingException, URISyntaxException {
                log.info("aptTradeDetailReader 호출");
                if (lawdIndex < lawdCodes.length) {
                    String lawdCode = lawdCodes[lawdIndex];
                    String ymd = ymdParams[ymdIndex];
                    log.info("ymd: {}, lawCode: {}", ymd, lawdCode);
                    final String uri = String.format("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey=%s&pageNo=1&numOfRows=10&LAWD_CD=%s&DEAL_YMD=%s", ENCODED_API_KEY, lawdCode, ymd);
                    String response = webClient.get()
                            .uri(new URI(uri))
                            .accept(MediaType.APPLICATION_XML)
                            .retrieve()
                            .bodyToMono(String.class)
                            .blockOptional().orElse("");

                    // XML 파싱 라이브러리를 사용하여 response를 AptSaleDTO로 변환
                    XmlMapper xmlMapper = new XmlMapper();
                    AptSaleDTO aptSaleDTO = xmlMapper.readValue(response, AptSaleDTO.class);

                    ymdIndex++;
                    if (ymdIndex >= ymdParams.length) {
                        ymdIndex = 0;
                        lawdIndex++;
                    }

                    return aptSaleDTO;
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    public ItemWriter<AptSaleDTO> aptTradeDetailWriter() {
        return new ItemWriter<AptSaleDTO>() {
            @Override
            public void write(Chunk<? extends AptSaleDTO> items) throws Exception {
                log.info("aptTradeDetailWriter 호출");
                StepExecution stepExecution = StepSynchronizationManager.getContext().getStepExecution();
                if (stepExecution != null) {
                    ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
                    List<AptSaleDTO> aptSaleDTOList = (List<AptSaleDTO>) executionContext.get("aptSaleDTOList");
                    if (aptSaleDTOList == null) {
                        aptSaleDTOList = new ArrayList<>();
                    }
                    aptSaleDTOList.addAll(items.getItems());
                    executionContext.put("aptSaleDTOList", aptSaleDTOList);
                }
            }
        };
    }

    @Bean
    public ItemReader<AptSale> aptCoordinateReader() {

        return new ItemReader<AptSale>() {
            private Iterator<AptSale> iterator;

            @Override
            public AptSale read() throws JsonProcessingException {
                log.info("aptCoordinateReader 호출");
                if (iterator == null) {
                    List<AptSaleDTO> aptSaleDTOList = (List<AptSaleDTO>) StepSynchronizationManager.getContext()
                            .getStepExecution().getJobExecution().getExecutionContext().get("aptSaleDTOList");


                    List<AptSale> aptSaleList = new ArrayList<>();

                    // aptSaleDTOList의 각 항목에 대해 좌표 정보를 받아오는 로직 추가
                    for (AptSaleDTO item : aptSaleDTOList) {
                        for (AptSaleDTO.Item myItem : item.getBody().getItemList()) {
                            String legalSiCode=myItem.getLegalDongSigunguCode().substring(0,2); // 시군구 코드에서 시 부분만 뽑아오기 (11021 -> 11 (서울))
                            String roadName = myItem.getRoadName();
                            String mainCode = removeLeadingZeros(myItem.getRoadNameBuildingMainCode());
                            String subCode = removeLeadingZeros(myItem.getRoadNameBuildingSubCode());

                            String address="";

//                            log.info("legalsicode: {}",legalSiCode);
                            Region region = Region.fromCode(legalSiCode);
                            String regionName = region.getName();
//                            log.info("regionName: {}",regionName);


                            address+=regionName+" ";
                            if (!subCode.isEmpty()) {
                                address += roadName + " " + mainCode + "-" + subCode; // 싸피로 2-2
                            } else {
                                address += roadName + " " + mainCode; // 싸피로 2
                            }
//                            System.out.println(address);

                            String url = "https://api.vworld.kr/req/address?service=address&request=getcoord&version=2.0&crs=epsg:4326&address=" + address + "&refine=true&simple=false&format=xml&type=road&key=92B84E44-5710-3F5A-9AE1-6F3CF087E8EC";

                            // WebClient를 사용하여 데이터 받아오기
                            String response = webClient.get()
                                    .uri(url)
                                    .accept(MediaType.APPLICATION_XML)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block();

                            // XML 파싱
                            XmlMapper xmlMapper = new XmlMapper();
                            JsonNode rootNode = xmlMapper.readTree(response);

                            // 필요한 값 추출
                            String text = rootNode.path("refined").path("text").asText();
                            String x = rootNode.path("result").path("point").path("x").asText();
                            String y = rootNode.path("result").path("point").path("y").asText();
                            String detail = rootNode.path("refined").path("structure").path("detail").asText();

                            log.info("{} {} {} {} {},{},{}",text,x,y,detail,myItem.getYear(),myItem.getMonth(),myItem.getDay());
//                            System.out.println(text+" "+x+" "+" "+y+" "+detail);
//                            System.out.println("myItem = " + myItem);

                            if (x.isEmpty() || y.isEmpty()) {
                                log.warn("좌표 정보가 없어 건너뜁니다. 주소: {}", address);
                                continue; // 현재 반복을 건너뛰고 다음 반복으로 이동
                            }

                            AptSale aptSale = AptSale.builder()
                                    .aptName(myItem.getApartment())
                                    .dealAmount(Integer.parseInt(myItem.getDealAmount().trim().replace(",", "")))
                                    .constructionYear(myItem.getConstructionYear())
                                    .roadName(myItem.getRoadName())
                                    .roadNameBonbun(myItem.getRoadNameBuildingMainCode())
                                    .roadNameBubun(myItem.getRoadNameBuildingSubCode())
                                    .roadNameAddress(address)
                                    .dong(myItem.getDong())
                                    .legalDong(myItem.getLegalDong())
                                    .dongcode(myItem.getLegalDongSigunguCode() + myItem.getLegalDongEupmyeondongCode())
                                    .dealYear(myItem.getYear())
                                    .dealMonth(myItem.getMonth())
                                    .dealDay(myItem.getDay())
                                    .exclusiveArea(myItem.getExclusiveArea())
                                    .floor(myItem.getFloor())
                                    .latitude(Double.parseDouble(y))
                                    .longitude(Double.parseDouble(x))
                                    .sidoName(address)
                                    .aptCode(myItem.getLegalDongSigunguCode() + myItem.getLegalDongEupmyeondongCode() +
                                            myItem.getRoadNameBuildingMainCode() + myItem.getRoadNameBuildingSubCode())
                                    .build();

                            aptSaleList.add(aptSale);

                        }
                    }

                    iterator = aptSaleList.iterator();
                }

                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    return null;
                }
            }
        };
    }

    public String removeLeadingZeros(String code) {
        return code.replaceFirst("^0+", "");
    }

    @Bean
    public ItemProcessor<AptSaleDTO, AptSaleDTO> aptCoordinateProcessor() {
        return item -> {
            // 좌표 정보를 받아오는 로직을 ItemReader로 이동했으므로 여기서는 별도의 처리 X.
            return item;
        };
    }

    @Bean
    public ItemWriter<AptSale> aptCoordinateWriter() {

        return items -> {
            log.info("aptCoordinateWriter 호출");
            aptSaleRepository.saveAll(items);
            for (AptSale item : items) {
//                memoryAptSaleRepository.save(item);
                log.info("{}",item);
            }
        };
    }
}