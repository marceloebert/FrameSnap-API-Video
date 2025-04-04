package com.fiap.framesnap.entities.video;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VideoStatusTest {

    @Test
    void fromString_WithValidStatus_ShouldReturnCorrectEnum() {
        assertEquals(VideoStatus.PENDING_UPLOAD, VideoStatus.fromString("PENDING_UPLOAD"));
        assertEquals(VideoStatus.UPLOADING, VideoStatus.fromString("UPLOADING"));
        assertEquals(VideoStatus.UPLOADED, VideoStatus.fromString("UPLOADED"));
        assertEquals(VideoStatus.PROCESSING, VideoStatus.fromString("PROCESSING"));
        assertEquals(VideoStatus.COMPLETED, VideoStatus.fromString("COMPLETED"));
        assertEquals(VideoStatus.ERROR, VideoStatus.fromString("ERROR"));
    }

    @Test
    void fromString_WithInvalidStatus_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            VideoStatus.fromString("INVALID_STATUS");
        });
    }

    @Test
    void fromString_WithNullStatus_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            VideoStatus.fromString(null);
        });
    }

    @Test
    void fromString_WithEmptyStatus_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            VideoStatus.fromString("");
        });
    }

    @Test
    void fromString_WithLowercaseStatus_ShouldReturnCorrectEnum() {
        assertEquals(VideoStatus.PENDING_UPLOAD, VideoStatus.fromString("pending_upload"));
        assertEquals(VideoStatus.UPLOADING, VideoStatus.fromString("uploading"));
        assertEquals(VideoStatus.UPLOADED, VideoStatus.fromString("uploaded"));
        assertEquals(VideoStatus.PROCESSING, VideoStatus.fromString("processing"));
        assertEquals(VideoStatus.COMPLETED, VideoStatus.fromString("completed"));
        assertEquals(VideoStatus.ERROR, VideoStatus.fromString("error"));
    }

    @Test
    void toString_ShouldReturnUppercaseString() {
        assertEquals("PENDING_UPLOAD", VideoStatus.PENDING_UPLOAD.toString());
        assertEquals("UPLOADING", VideoStatus.UPLOADING.toString());
        assertEquals("UPLOADED", VideoStatus.UPLOADED.toString());
        assertEquals("PROCESSING", VideoStatus.PROCESSING.toString());
        assertEquals("COMPLETED", VideoStatus.COMPLETED.toString());
        assertEquals("ERROR", VideoStatus.ERROR.toString());
    }

    @Test
    void getStatus_ShouldReturnCorrectString() {
        assertEquals("PENDING_UPLOAD", VideoStatus.PENDING_UPLOAD.getStatus());
        assertEquals("UPLOADING", VideoStatus.UPLOADING.getStatus());
        assertEquals("UPLOADED", VideoStatus.UPLOADED.getStatus());
        assertEquals("PROCESSING", VideoStatus.PROCESSING.getStatus());
        assertEquals("COMPLETED", VideoStatus.COMPLETED.getStatus());
        assertEquals("ERROR", VideoStatus.ERROR.getStatus());
    }
} 