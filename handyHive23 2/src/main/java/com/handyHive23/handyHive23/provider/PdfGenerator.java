package com.handyHive23.handyHive23.provider;

import com.handyHive23.handyHive23.booking.Booking;
import com.handyHive23.handyHive23.review.Review;
import com.handyHive23.handyHive23.service.ServiceComment;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {

    public static ByteArrayOutputStream createStatisticsPdf(String businessName,
                                                        int postCount,
                                                        int commentCount,
                                                        int savedCount,
                                                        double avgRating,
                                                        List<ServiceComment> comments,
                                                        List<Review> reviews,
                                                        List<Booking> bookings) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, out);
    document.open();

    Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLACK);
    Font statFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.DARK_GRAY);
    Font normalFont = new Font(Font.HELVETICA, 12);
    Font grayItalic = new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY);
    DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Paragraph title = new Paragraph("HandyHive Business Report\n\n", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    document.add(new Paragraph("Business Name: " + businessName, statFont));
    document.add(new Paragraph("Generated On: " + java.time.LocalDateTime.now(), normalFont));
    document.add(Chunk.NEWLINE);

    document.add(new Paragraph("📊 Statistics Summary", statFont));
    document.add(new Paragraph("Total Posts: " + postCount, normalFont));
    document.add(new Paragraph("Total Comments: " + commentCount, normalFont));
    document.add(new Paragraph("Saved by Customers: " + savedCount, normalFont));
    document.add(new Paragraph("Average Rating: " + (avgRating > 0 ? String.format("%.2f", avgRating) : "N/A"), normalFont));
    document.add(Chunk.NEWLINE);

    document.add(new Paragraph("🗨️ Recent Comments", statFont));
    if (comments.isEmpty()) {
        document.add(new Paragraph("No comments found.", normalFont));
    } else {
        for (ServiceComment c : comments) {
            String username = c.getCustomer() != null ? c.getCustomer().getName() : "Unknown";
            String date = c.getCommentedAt() != null ? c.getCommentedAt().format(dateFmt) : "N/A";
            Paragraph p = new Paragraph("• " + username + ": " + c.getContent(), normalFont);
            p.add(new Chunk(" [" + date + "]", grayItalic));
            document.add(p);
        }
    }

    document.add(Chunk.NEWLINE);
    document.add(new Paragraph("⭐ Reviews", statFont));
    if (reviews.isEmpty()) {
        document.add(new Paragraph("No reviews found.", normalFont));
    } else {
        for (Review r : reviews) {
            String username = r.getCustomer() != null ? r.getCustomer().getName() : "Anonymous";
            String date = r.getReviewDate() != null ? r.getReviewDate().toString() : "N/A";
            document.add(new Paragraph("• " + username + " rated " + r.getRating() + "/5 - " + r.getComment() + " [" + date + "]", normalFont));
        }
    }

    document.add(Chunk.NEWLINE);
    document.add(new Paragraph("📅 Bookings", statFont));
    if (bookings.isEmpty()) {
        document.add(new Paragraph("No bookings yet.", normalFont));
    } else {
        for (Booking b : bookings) {
            String customer = b.getCustomer() != null ? b.getCustomer().getName() : "Anonymous";
            String date = b.getAppointmentDate() != null ? b.getAppointmentDate().toString() : "N/A";
            String time = b.getAppointmentTime() != null ? b.getAppointmentTime().toString() : "N/A";
            document.add(new Paragraph("• " + customer + " booked for " + date + " at " + time, normalFont));
        }
    }

    document.close();
    return out;
}

}
