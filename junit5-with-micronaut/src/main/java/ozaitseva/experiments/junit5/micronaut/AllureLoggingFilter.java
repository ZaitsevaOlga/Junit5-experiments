package ozaitseva.experiments.junit5.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.ClientFilterChain;
import io.micronaut.http.filter.HttpClientFilter;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

@Filter
class AllureLoggingFilter implements HttpClientFilter {
    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(MutableHttpRequest<?> request, ClientFilterChain chain) {
        final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();
        /**
         * The code based on suggestion that requests/responses headers and body are available for casting to String
         */
        final HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder
                .create("Request", request.getPath())
                .setMethod(request.getMethodName())
                .setHeaders(request.getHeaders().asMap(String.class, String.class));
        if (request.getBody(String.class).isPresent()) {
            requestAttachmentBuilder.setBody(request.getBody(String.class).get());
        }
        final HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        processor.addAttachment(requestAttachment, new FreemarkerAttachmentRenderer(requestTemplatePath));

        /**
         * Cast Publisher to Flowable to handle responses for allure attachments
         */
        Flowable<HttpResponse<?>> responseFlowable = Flowable.fromPublisher(chain.proceed(request));
        return responseFlowable
                .doOnNext(httpResponse -> {
                    final HttpResponseAttachment.Builder responseAttachmentBuilder = HttpResponseAttachment.Builder
                            .create("Response")
                            .setResponseCode(httpResponse.code())
                            .setHeaders(httpResponse.getHeaders().asMap(String.class, String.class));
                    if (httpResponse.getBody(String.class).isPresent()) {
                        responseAttachmentBuilder.setBody(httpResponse.getBody(String.class).get());
                    }

                    final HttpResponseAttachment responseAttachment = responseAttachmentBuilder.build();
                    processor.addAttachment(responseAttachment, new FreemarkerAttachmentRenderer(responseTemplatePath));
                });
    }


}
