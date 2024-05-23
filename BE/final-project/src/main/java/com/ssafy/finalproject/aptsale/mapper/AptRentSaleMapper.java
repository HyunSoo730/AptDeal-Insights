//package com.ssafy.finalproject.aptsale.mapper;
//
//import com.ssafy.finalproject.aptsale.entity.AptRentSale;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface AptRentSaleMapper {
//
//    @Select("SELECT * FROM apt_rent_sale WHERE id = #{id}")
//    AptRentSale selectAptRentSaleById(Long id);
//
//    @Select("SELECT * FROM apt_rent_sale")
//    List<AptRentSale> selectAllAptRentSales();
//
//    @Insert("INSERT INTO apt_rent_sale (renewalRightUsage, constructionYear, contractType, contractPeriod, year, legalDong, depositAmount, apartmentName, month, monthlyRent, day, exclusiveArea, previousContractDeposit, previousContractRent, lotNumber, regionCode, floor) VALUES (#{renewalRightUsage}, #{constructionYear}, #{contractType}, #{contractPeriod}, #{year}, #{legalDong}, #{depositAmount}, #{apartmentName}, #{month}, #{monthlyRent}, #{day}, #{exclusiveArea}, #{previousContractDeposit}, #{previousContractRent}, #{lotNumber}, #{regionCode}, #{floor})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    void insertAptRentSale(AptRentSale aptRentSale);
//
//    @Update("UPDATE apt_rent_sale SET renewalRightUsage=#{renewalRightUsage}, constructionYear=#{constructionYear}, contractType=#{contractType}, contractPeriod=#{contractPeriod}, year=#{year}, legalDong=#{legalDong}, depositAmount=#{depositAmount}, apartmentName=#{apartmentName}, month=#{month}, monthlyRent=#{monthlyRent}, day=#{day}, exclusiveArea=#{exclusiveArea}, previousContractDeposit=#{previousContractDeposit}, previousContractRent=#{previousContractRent}, lotNumber=#{lotNumber}, regionCode=#{regionCode}, floor=#{floor} WHERE id=#{id}")
//    void updateAptRentSale(AptRentSale aptRentSale);
//
//    @Delete("DELETE FROM apt_rent_sale WHERE id = #{id}")
//    void deleteAptRentSale(Long id);
//}
