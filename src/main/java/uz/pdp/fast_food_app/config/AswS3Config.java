package uz.pdp.fast_food_app.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AswS3Config {
    @Value("${aws.access.key}")
    private String ACCESS_KEY;
    @Value("${aws.secret.key}")
    private String SECRET_KEY;

    @Bean
    public AmazonS3 amazonS3(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
