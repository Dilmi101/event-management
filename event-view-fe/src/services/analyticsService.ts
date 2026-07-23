const ANALYTICS_URL = "http://localhost:8085/analytics";

export async function sendAnalytics(
    eventType: string,
    page: string
) {

    const payload = {
        eventType,
        page,
        sessionId: crypto.randomUUID(),
        browser: navigator.userAgent
    };

    try {
        await fetch(ANALYTICS_URL,{
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(payload)
        });
    } catch(error){
        console.error(error);
    }
}