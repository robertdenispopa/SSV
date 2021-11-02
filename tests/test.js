import junit from "junit";
var it = junit();
(async () => {
    // Async tests.
    it("test 1", () =>
        // We use `it.eq` to assert on both simple type and complex object.
        it.eq("ok", "ok")
    );
 
    it("test 2", async () => {
        // No more callback hell while testing async functions.
        await new junit.Promise(r => setTimeout(r, 1000));
 
        return it.eq({ a: 1, b: 2 }, { a: 1, b: 2 });
    });
 
    // Run sync tests within the main async flow.
    // await it("test 3", (after) =>
    //     after(() => {
    //         // do some clean work after the test
    //     });
 
    //     it.eq("ok", "ok")
    // );
 
    it.run();
})();