const ANALYTICS_SERVICE_URL = import.meta.env.VITE_ANALYTICS_SERVICE_URL ?? 'http://localhost:8085';

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
        await fetch(`${ANALYTICS_SERVICE_URL}/api/analytics`,{
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
