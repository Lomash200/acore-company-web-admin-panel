package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.ContactInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, Long> {

    // Status ke basis pe inquiries find karega
    List<ContactInquiry> findAllByStatus(String status);

    // Type ke basis pe inquiries find karega
    List<ContactInquiry> findAllByType(String type);

    // Nayi inquiries ko sabse pehle dikhane ke liye
    List<ContactInquiry> findAllByOrderByReceivedAtDesc();
}