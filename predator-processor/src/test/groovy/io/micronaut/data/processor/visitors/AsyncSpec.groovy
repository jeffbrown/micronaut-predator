package io.micronaut.data.processor.visitors

import io.micronaut.data.intercept.annotation.PredatorMethod
import io.micronaut.data.intercept.async.CountAsyncInterceptor
import io.micronaut.data.intercept.async.DeleteAllAsyncInterceptor
import io.micronaut.data.intercept.async.ExistsByAsyncInterceptor
import io.micronaut.data.intercept.async.FindAllAsyncInterceptor
import io.micronaut.data.intercept.async.FindByIdAsyncInterceptor
import io.micronaut.data.intercept.async.FindOneAsyncInterceptor
import io.micronaut.data.intercept.async.FindPageAsyncInterceptor
import io.micronaut.data.intercept.async.FindSliceAsyncInterceptor
import io.micronaut.data.intercept.async.SaveAllAsyncInterceptor
import io.micronaut.data.intercept.async.SaveEntityAsyncInterceptor
import io.micronaut.data.intercept.async.SaveOneAsyncInterceptor
import io.micronaut.data.intercept.async.UpdateAsyncInterceptor
import spock.lang.Unroll

class AsyncSpec extends AbstractPredatorSpec {

    @Unroll
    void "test async method with completion stage #method"() {
        given:
        def repository = buildRepository('test.MyInterface', """

import io.micronaut.data.model.entities.Person;
import java.util.concurrent.CompletionStage;
import io.micronaut.data.annotation.*;
import io.micronaut.data.model.*;
import java.util.*;

@Repository
interface MyInterface extends GenericRepository<Person, Long> {

    $returnType $method($arguments);
}
"""
        )

        expect:
        repository.findPossibleMethods(method)
                .findFirst()
                .get()
                .synthesize(PredatorMethod)
                .interceptor() == interceptor

        where:
        method         | returnType                       | arguments               | interceptor
        "list"         | "CompletionStage<Page<Person>>"  | "Pageable pageable"     | FindPageAsyncInterceptor
        "list"         | "CompletionStage<Slice<Person>>" | "Pageable pageable"     | FindSliceAsyncInterceptor
        "findByName"   | "CompletionStage<Person>"        | "String name"           | FindOneAsyncInterceptor
        "findByName"   | "CompletionStage<List<Person>>"  | "String name"           | FindAllAsyncInterceptor
        "find"         | "CompletionStage<List<Person>>"  | "String name"           | FindAllAsyncInterceptor
        "find"         | "CompletionStage<Person>"        | "String name"           | FindOneAsyncInterceptor
        "count"        | "CompletionStage<Long>"          | "String name"           | CountAsyncInterceptor
        "countByName"  | "CompletionStage<Long>"          | "String name"           | CountAsyncInterceptor
        "delete"       | "CompletionStage<Boolean>"       | "String name"           | DeleteAllAsyncInterceptor
        "delete"       | "CompletionStage<Void>"          | "String name"           | DeleteAllAsyncInterceptor
        "deleteByName" | "CompletionStage<Boolean>"       | "String name"           | DeleteAllAsyncInterceptor
        "existsByName" | "CompletionStage<Boolean>"       | "String name"           | ExistsByAsyncInterceptor
        "findById"     | "CompletionStage<Person>"        | "Long id"               | FindByIdAsyncInterceptor
        "save"         | "CompletionStage<Person>"        | "Person person"         | SaveEntityAsyncInterceptor
        "save"         | "CompletionStage<Person>"        | "String name"           | SaveOneAsyncInterceptor
        "save"         | "CompletionStage<List<Person>>"  | "List<Person> entities" | SaveAllAsyncInterceptor
        "updateByName" | "CompletionStage<Boolean>"       | "String name, int age"  | UpdateAsyncInterceptor
        "update"       | "CompletionStage<Boolean>"       | "@Id Long id, int age"  | UpdateAsyncInterceptor
    }

    @Unroll
    void "test async method with completable future #method"() {
        given:
        def repository = buildRepository('test.MyInterface', """

import io.micronaut.data.model.entities.Person;
import java.util.concurrent.*;
import io.micronaut.data.model.*;
import java.util.*;

@Repository
interface MyInterface extends GenericRepository<Person, Long> {

    $returnType $method($arguments);
}
"""
        )

        expect:
        repository.findPossibleMethods(method)
                .findFirst()
                .get()
                .synthesize(PredatorMethod)
                .interceptor() == interceptor

        where:
        method         | returnType                         | arguments           | interceptor
        "list"         | "CompletableFuture<Page<Person>>"  | "Pageable pageable" | FindPageAsyncInterceptor
        "list"         | "CompletableFuture<Slice<Person>>" | "Pageable pageable" | FindSliceAsyncInterceptor
        "findByName"   | "CompletableFuture<Person>"        | "String name"       | FindOneAsyncInterceptor
        "findByName"   | "CompletableFuture<List<Person>>"  | "String name"       | FindAllAsyncInterceptor
        "find"         | "CompletableFuture<List<Person>>"  | "String name"       | FindAllAsyncInterceptor
        "find"         | "CompletableFuture<Person>"        | "String name"       | FindOneAsyncInterceptor
        "count"        | "CompletableFuture<Long>"          | "String name"       | CountAsyncInterceptor
        "delete"       | "CompletableFuture<Boolean>"       | "String name"       | DeleteAllAsyncInterceptor
        "delete"       | "CompletableFuture<Void>"          | "String name"       | DeleteAllAsyncInterceptor
        "deleteByName" | "CompletableFuture<Boolean>"       | "String name"       | DeleteAllAsyncInterceptor
        "existsByName" | "CompletableFuture<Boolean>"       | "String name"       | ExistsByAsyncInterceptor
        "findById"     | "CompletableFuture<Person>"        | "Long id"           | FindByIdAsyncInterceptor
    }

    @Unroll
    void "test async method with future #method"() {
        given:
        def repository = buildRepository('test.MyInterface', """

import io.micronaut.data.model.entities.Person;
import java.util.concurrent.*;
import io.micronaut.data.model.*;
import java.util.*;

@Repository
interface MyInterface extends GenericRepository<Person, Long> {

    $returnType $method($arguments);
}
"""
        )

        expect:
        repository.findPossibleMethods(method)
                .findFirst()
                .get()
                .synthesize(PredatorMethod)
                .interceptor() == interceptor

        where:
        method         | returnType              | arguments           | interceptor
        "list"         | "Future<Page<Person>>"  | "Pageable pageable" | FindPageAsyncInterceptor
        "list"         | "Future<Slice<Person>>" | "Pageable pageable" | FindSliceAsyncInterceptor
        "findByName"   | "Future<Person>"        | "String name"       | FindOneAsyncInterceptor
        "findByName"   | "Future<List<Person>>"  | "String name"       | FindAllAsyncInterceptor
        "find"         | "Future<List<Person>>"  | "String name"       | FindAllAsyncInterceptor
        "find"         | "Future<Person>"        | "String name"       | FindOneAsyncInterceptor
        "count"        | "Future<Long>"          | "String name"       | CountAsyncInterceptor
        "delete"       | "Future<Boolean>"       | "String name"       | DeleteAllAsyncInterceptor
        "delete"       | "Future<Void>"          | "String name"       | DeleteAllAsyncInterceptor
        "deleteByName" | "Future<Boolean>"       | "String name"       | DeleteAllAsyncInterceptor
        "existsByName" | "Future<Boolean>"       | "String name"       | ExistsByAsyncInterceptor
        "findById"     | "Future<Person>"        | "Long id"           | FindByIdAsyncInterceptor
    }
}