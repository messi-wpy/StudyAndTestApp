package com.example.studyandtestapp.CustomView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import okio.Utf8;

public class RequestBodyWithProgressBar extends RequestBody {



    public interface ProgressListener{
        void  updataPragressBar(long size,long current);

    }

    private String content;
    private File file;
    private MediaType contentType;
    private  ProgressListener progress;

    //标志传送的是文件还是string
    private boolean isFile;
    public  RequestBodyWithProgressBar(String content,MediaType type,ProgressListener progress){
        this.content=content;
        this.contentType=type;
        this.progress=progress;
        isFile=false;
    }

    public RequestBodyWithProgressBar(File file,MediaType type,ProgressListener progress){
        this.file=file;
        this.contentType=type;
        this.progress=progress;

        isFile=true;

    }

    /**
     * file
     *  @Override public void writeTo(BufferedSink sink) throws IOException {
     *         Source source = null;
     *         try {
     *           source = Okio.source(file);
     *           sink.writeAll(source);
     *         } finally {
     *           Util.closeQuietly(source);
     *         }
     *       }
     * @return
     */

    /**
     * String
     * public static RequestBody create(final @Nullable MediaType contentType, final byte[] content,
     *       final int offset, final int byteCount) {
     *     if (content == null) throw new NullPointerException("content == null");
     *     Util.checkOffsetAndCount(content.length, offset, byteCount);
     *     return new RequestBody() {
     *       @Override public @Nullable MediaType contentType() {
     *         return contentType;
     *       }
     *
     *       @Override public long contentLength() {
     *         return byteCount;
     *       }
     *
     *       @Override public void writeTo(BufferedSink sink) throws IOException {
     *         sink.write(content, offset, byteCount);
     *       }
     *     };
     *   }
     *
     *
     * @return
     */

    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength(){
        if (isFile)
            return file.length();
        else
            return content.length();

    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (progress==null) throw new NullPointerException("progress==null");
        if (!isFile){
            Charset charset= Util.UTF_8;
            if (this.contentType()!=null){
                charset=contentType().charset();
                if (charset==null) {
                    charset = Util.UTF_8;
                    contentType=MediaType.parse(contentType+"; charset=utf-8");
                }
            }
            byte[]bytes=content.getBytes(charset);
            int  block=1024;
            int total=content.length();
            int current=0;
            while (current+block<total){
                sink.write(bytes,current,block);
                current+=block;
                sink.flush();
                progress.updataPragressBar(total,current);

            }
            int rest=total-current;
            sink.write(bytes,current,rest);
            sink.flush();

        }
        else {
            Source source=null;
            try {
                source= Okio.source(file);
                int block=1024;

                long total=file.length();
                long read=source.read(sink.buffer(),block);
                sink.flush();
                while (read<total){
                    read+=source.read(sink.buffer(),block);
                    sink.flush();
                    progress.updataPragressBar(total,read);
                }

            }finally {
                Util.closeQuietly(source);
            }

        }

    }
}
