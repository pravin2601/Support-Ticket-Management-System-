package com.example.supportticket.repository;
import com.example.supportticket.model.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface TicketRepository extends JpaRepository<Ticket,Long>{
 @Query("select t from Ticket t where (:keyword is null or :keyword='' or lower(t.title) like lower(concat('%',:keyword,'%')) or lower(t.description) like lower(concat('%',:keyword,'%'))) and (:status is null or t.status=:status) order by t.updatedAt desc")
 List<Ticket> search(@Param("keyword") String keyword,@Param("status") TicketStatus status);
}
