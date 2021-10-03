package com.andreiev.readinglist;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
// inject with "amazon" prefixed properties
@ConfigurationProperties("amazon")
@Getter
@Setter
public class AmazonProperties {

    private String associateId;
}
