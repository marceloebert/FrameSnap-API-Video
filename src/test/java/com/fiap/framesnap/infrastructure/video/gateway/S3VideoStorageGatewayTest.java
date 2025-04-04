package com.fiap.framesnap.infrastructure.video.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class S3VideoStorageGatewayTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    private S3VideoStorageGateway s3VideoStorageGateway;
    private final String BUCKET_NAME = "test-bucket";
    private final Region REGION = Region.US_EAST_1;

    @BeforeEach
    void setUp() {
        s3VideoStorageGateway = new S3VideoStorageGateway(s3Client, s3Presigner, BUCKET_NAME, REGION);
    }

    @Test
    void uploadVideo_WhenSuccessful_ShouldUploadToS3() throws IOException {
        // Arrange
        String fileName = "test.mp4";
        byte[] content = "test content".getBytes();
        InputStream videoStream = new ByteArrayInputStream(content);

        PutObjectResponse response = PutObjectResponse.builder().build();
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
            .thenReturn(response);

        // Act
        s3VideoStorageGateway.uploadVideo(fileName, videoStream);

        // Assert
        verify(s3Client).putObject(
            argThat((PutObjectRequest request) -> 
                request.bucket().equals(BUCKET_NAME) &&
                request.key().equals(fileName)
            ),
            any(RequestBody.class)
        );
    }

    @Test
    void uploadVideo_WhenS3Fails_ShouldThrowException() throws IOException {
        // Arrange
        String fileName = "test.mp4";
        byte[] content = "test content".getBytes();
        InputStream videoStream = new ByteArrayInputStream(content);

        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
            .thenThrow(S3Exception.builder().message("Upload failed").build());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            s3VideoStorageGateway.uploadVideo(fileName, videoStream);
        });

        assertTrue(exception.getMessage().contains("Erro ao fazer upload para S3"));
    }

    @Test
    void downloadVideo_WhenSuccessful_ShouldReturnInputStream() {
        // Arrange
        String fileName = "test.mp4";
        ResponseInputStream<GetObjectResponse> mockResponseInputStream = mock(ResponseInputStream.class);
        GetObjectResponse response = GetObjectResponse.builder().build();

        when(s3Client.getObject(any(GetObjectRequest.class)))
            .thenReturn(mockResponseInputStream);

        // Act
        InputStream result = s3VideoStorageGateway.downloadVideo(fileName);

        // Assert
        assertNotNull(result);
        verify(s3Client).getObject(
            argThat((GetObjectRequest request) -> 
                request.bucket().equals(BUCKET_NAME) &&
                request.key().equals(fileName)
            )
        );
    }

    @Test
    void getDownloadUrl_ShouldReturnCorrectUrl() {
        // Arrange
        String videoKey = "test.mp4";
        String expectedUrl = String.format("https://%s.s3.amazonaws.com/%s", BUCKET_NAME, videoKey);

        // Act
        String result = s3VideoStorageGateway.getDownloadUrl(videoKey);

        // Assert
        assertEquals(expectedUrl, result);
    }

    @Test
    void generatePresignedUploadUrl_WhenSuccessful_ShouldReturnUrl() {
        // Arrange
        String objectKey = "test.mp4";
        URL presignedUrl = mock(URL.class);
        when(presignedUrl.toString()).thenReturn("https://presigned-url");

        PresignedPutObjectRequest presignedRequest = mock(PresignedPutObjectRequest.class);
        when(presignedRequest.url()).thenReturn(presignedUrl);

        when(s3Presigner.presignPutObject(any(PutObjectPresignRequest.class)))
            .thenReturn(presignedRequest);

        // Act
        String result = s3VideoStorageGateway.generatePresignedUploadUrl(objectKey);

        // Assert
        assertNotNull(result);
        assertEquals("https://presigned-url", result);
        verify(s3Presigner).presignPutObject(any(PutObjectPresignRequest.class));
    }
} 